package com.mathem.deliverydates.utils;

import com.mathem.deliverydates.models.Product;
import com.mathem.deliverydates.models.Product.ProductType;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

  public class DeliveryUtils {

    public static boolean isDeliveryWeekdayValid(LocalDate date, List<Product> products) {
      // Check if all the products can be delivered on the given weekday
      return products.stream()
          .allMatch(product -> product.getDeliveryDays().contains(date.getDayOfWeek()));
    }

    public static boolean isDeliveryDateValid(LocalDate fromDate, LocalDate targetDate,
        List<Product> products) {
      // Check if all products can be delivered on the given date, based on days in advance
      return products.stream()
          .allMatch(product -> targetDate.isAfter(fromDate.plusDays(product.getDaysInAdvance()-1)));
    }

    public static List<LocalDate> getGreenDeliveryDates(LocalDate startDate, LocalDate endDate) {
      // All Wednesdays
      List<LocalDate> greenDeliveryDates = new ArrayList<>();
      for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
        if (date.getDayOfWeek() == DayOfWeek.WEDNESDAY) {
          greenDeliveryDates.add(date);
        }
      }
      return greenDeliveryDates;
    }

    public static String formatDeliveryDate(LocalDate date) {
      DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
      return (date.atStartOfDay(ZoneOffset.ofHours(1))).format(formatter);
    }

    public static boolean isExternalProduct(Product product) {
      return product.getProductType() == ProductType.EXTERNAL;
    }

    public static boolean isTemporaryProduct(Product product) {
      return product.getProductType() == ProductType.TEMPORARY;
    }
  }

