package com.mathem.deliverydates.utils;

import com.mathem.deliverydates.models.Product;
import com.mathem.deliverydates.models.Product.ProductType;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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

    // Temporary products can only be ordered within the current week (Mon-Sun).
    public static boolean isValidDateForTemporaryProduct(LocalDate fromDate, LocalDate targetDate,
        Product product) {
      if (product.getProductType() != ProductType.TEMPORARY) {
        return true;
      }
      List<LocalDate> validDates = new ArrayList<>();

      Calendar now = Calendar.getInstance();
      now.setTime(Date.from(fromDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
      int delta = -now.get(GregorianCalendar.DAY_OF_WEEK) + 2; //add 2 if your week start on monday
      now.add(Calendar.DAY_OF_MONTH, delta ); // get beginning of week
      for (int i = 0; i < 7; i++)
      {
        validDates.add(now.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        now.add(Calendar.DAY_OF_MONTH, 1);
      }

      return validDates.contains(targetDate);
    }
  }

