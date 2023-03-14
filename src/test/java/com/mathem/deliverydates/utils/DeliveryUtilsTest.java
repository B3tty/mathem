package com.mathem.deliverydates.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.mathem.deliverydates.models.Product;
import com.mathem.deliverydates.models.Product.ProductType;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

public class DeliveryUtilsTest {

  private static final LocalDate today = LocalDate.of(2023, 3, 13);

  private static final List<LocalDate> currentWeek = new ArrayList<>(List.of(LocalDate.of(2023, 3, 13), LocalDate.of(2023, 3, 14), LocalDate.of(2023, 3, 15), LocalDate.of(2023, 3, 16), LocalDate.of(2023, 3, 17), LocalDate.of(2023, 3, 18), LocalDate.of(2023, 3, 19)));

  private static final Product NORMAL_PRODUCT = new Product(1, "Product 1",
        Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY),
        ProductType.NORMAL, 3);

    private static final Product EXTERNAL_PRODUCT = new Product(2, "Product 2",
        Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY),
        ProductType.EXTERNAL, 5);

    private static final Product TEMPORARY_PRODUCT = new Product(3, "Product 3",
        Arrays.asList(DayOfWeek.WEDNESDAY, DayOfWeek.SATURDAY),
        ProductType.TEMPORARY, 1);

    private static final List<Product> PRODUCTS = Arrays.asList(NORMAL_PRODUCT, EXTERNAL_PRODUCT, TEMPORARY_PRODUCT);

  @Test
  public void testIsDeliveryWeekdayValid_WhenNoProductMatch_ShouldReturnFalse() {
    LocalDate date7 = LocalDate.of(2023, 3, 19); // Sunday
    assertFalse(DeliveryUtils.isDeliveryWeekdayValid(date7, PRODUCTS));
  }

  @Test
  public void testIsDeliveryWeekdayValid_WhenNotAllProductsMatch_ShouldReturnFalse() {
    LocalDate date1 = LocalDate.of(2023, 3, 13); // Monday
    assertFalse(DeliveryUtils.isDeliveryWeekdayValid(date1, PRODUCTS));
  }

  @Test
  public void testIsDeliveryWeekdayValid_WhenAllProductsMatch_ShouldReturnTrue() {
    LocalDate date3 = LocalDate.of(2023, 3, 15); // Wednesday
    assertTrue(DeliveryUtils.isDeliveryWeekdayValid(date3, PRODUCTS));
  }

  @Test
  public void testIsDeliveryDateValid_WhenAllProductsMatch_ShouldReturnTrue() {
    LocalDate date3 = LocalDate.of(2023, 3, 18); // 5 days in advance
    assertTrue(DeliveryUtils.isDeliveryDateValid(today, date3, PRODUCTS));
  }

  @Test
  public void testIsDeliveryDateValid_WhenNoProductMatch_ShouldReturnFalse() {
    LocalDate date1 = LocalDate.of(2023, 3, 13); // 0 day in advance
    assertFalse(DeliveryUtils.isDeliveryDateValid(today, date1, PRODUCTS));
  }

  @Test
  public void testIsDeliveryDateValid_WhenSomeProductsMatch_ShouldReturnFalse() {
    LocalDate date2 = LocalDate.of(2023, 3, 16); // 3 days in advance
    assertFalse(DeliveryUtils.isDeliveryDateValid(today, date2, PRODUCTS));
  }

  @Test
  public void testGetGreenDeliveryDates() {
    LocalDate startDate = LocalDate.of(2023, 3, 13);
    LocalDate endDate = LocalDate.of(2023, 3, 19);

    List<LocalDate> expectedDates = Arrays.asList(LocalDate.of(2023, 3, 15));
    List<LocalDate> actualDates = DeliveryUtils.getGreenDeliveryDates(startDate, endDate);

    assertEquals(expectedDates, actualDates);
  }

  @Test
  public void testFormatDeliveryDate() {
    LocalDate date = LocalDate.of(2023, 3, 15);
    String expectedDateStr = "2023-03-15T00:00:00+01:00";
    String actualDateStr = DeliveryUtils.formatDeliveryDate(date);

    assertEquals(expectedDateStr, actualDateStr);
  }

  @Test
  public void testIsDeliveryDateValidForTemporaryProduct_WhenTemporaryProduct_AndTodayIsMonday_ShouldReturnTrueForWholeWeek() {
    for (LocalDate date : currentWeek) {
      assertTrue(DeliveryUtils.isDeliveryDateValidForTemporaryProduct(today, date, TEMPORARY_PRODUCT));
    }
    // Previous Sunday
    assertFalse(DeliveryUtils.isDeliveryDateValidForTemporaryProduct(today, LocalDate.of(2023, 3, 12), TEMPORARY_PRODUCT));
    // Next Monday
    assertFalse(DeliveryUtils.isDeliveryDateValidForTemporaryProduct(today, LocalDate.of(2023, 3, 20), TEMPORARY_PRODUCT));
  }

  @Test
  public void testIsDeliveryDateValidForTemporaryProduct_WhenTemporaryProduct_AndTodayIsNotMonday_ShouldReturnTrueForWholeWeek() {
    LocalDate fromDate = LocalDate.of(2023, 3, 15); // Middle of the week
    for (LocalDate date : currentWeek) {
      assertTrue(DeliveryUtils.isDeliveryDateValidForTemporaryProduct(fromDate, date, TEMPORARY_PRODUCT));
    }
    // Previous Sunday
    assertFalse(DeliveryUtils.isDeliveryDateValidForTemporaryProduct(fromDate, LocalDate.of(2023, 3, 12), TEMPORARY_PRODUCT));
    // Next Monday
    assertFalse(DeliveryUtils.isDeliveryDateValidForTemporaryProduct(fromDate, LocalDate.of(2023, 3, 20), TEMPORARY_PRODUCT));
  }

  @Test
  public void testIsDeliveryDateValidForTemporaryProduct_WhenNotTemporaryProduct_ShouldReturnTrueAlways() {
    LocalDate fromDate = LocalDate.of(2023, 3, 15); // Middle of the week
    for (LocalDate date : currentWeek) {
      assertTrue(DeliveryUtils.isDeliveryDateValidForTemporaryProduct(fromDate, date, EXTERNAL_PRODUCT));
      assertTrue(DeliveryUtils.isDeliveryDateValidForTemporaryProduct(fromDate, date, NORMAL_PRODUCT));
    }
    // Previous Sunday
    assertTrue(DeliveryUtils.isDeliveryDateValidForTemporaryProduct(fromDate, LocalDate.of(2023, 3, 12), EXTERNAL_PRODUCT));
    assertTrue(DeliveryUtils.isDeliveryDateValidForTemporaryProduct(fromDate, LocalDate.of(2023, 3, 12), NORMAL_PRODUCT));
    // Next Monday
    assertTrue(DeliveryUtils.isDeliveryDateValidForTemporaryProduct(fromDate, LocalDate.of(2023, 3, 20), EXTERNAL_PRODUCT));
    assertTrue(DeliveryUtils.isDeliveryDateValidForTemporaryProduct(fromDate, LocalDate.of(2023, 3, 20), NORMAL_PRODUCT));
  }

}
