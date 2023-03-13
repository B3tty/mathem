package com.mathem.deliverydates.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.mathem.deliverydates.models.Product;
import com.mathem.deliverydates.models.Product.ProductType;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import net.bytebuddy.asm.Advice.Local;
import org.junit.jupiter.api.Test;

public class DeliveryUtilsTest {

  private static final LocalDate today = LocalDate.of(2023, 3, 13);

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
  public void testIsExternalProduct_WhenExternal_ShouldReturnTrue() {
    assertTrue(DeliveryUtils.isExternalProduct(EXTERNAL_PRODUCT));
  }

  @Test
  public void testIsExternalProduct_WhenNormal_ShouldReturnFalse() {
    assertFalse(DeliveryUtils.isExternalProduct(NORMAL_PRODUCT));
  }

  @Test
  public void testIsExternalProduct_WhenTemporary_ShouldReturnFalse() {
    assertFalse(DeliveryUtils.isExternalProduct(TEMPORARY_PRODUCT));
  }

  @Test
  public void testIsTemporaryProduct_WhenTemporary_ShouldReturnTrue() {
    assertTrue(DeliveryUtils.isTemporaryProduct(TEMPORARY_PRODUCT));
  }

  @Test
  public void testIsTemporaryProduct_WhenNormal_ShouldReturnFalse() {
    assertFalse(DeliveryUtils.isTemporaryProduct(NORMAL_PRODUCT));
  }

  @Test
  public void testIsTemporaryProduct_WhenExternal_ShouldReturnFalse() {
    assertFalse(DeliveryUtils.isTemporaryProduct(EXTERNAL_PRODUCT));
  }
}
