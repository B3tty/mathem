package com.mathem.deliverydates;

import com.mathem.deliverydates.models.Product;
import com.mathem.deliverydates.models.Product.ProductType;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import com.mathem.deliverydates.models.DeliveryOption;
import com.mathem.deliverydates.services.DeliveryService;

public class Main {

  public static void main(String[] args) {
    System.out.println("Hello world!");
    DeliveryService deliveryService = new DeliveryService();

    // Create a list of products
    List<Product> productList = new ArrayList<>();
    productList.add(new Product(1234, "Product 1", List.of(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY,
        DayOfWeek.FRIDAY),
        ProductType.NORMAL,
        2));
    productList.add(new Product(5678, "Product 2", List.of(DayOfWeek.TUESDAY, DayOfWeek.THURSDAY),
        ProductType.EXTERNAL,
        5));
    productList.add(new Product(9012, "Product 3", List.of(DayOfWeek.MONDAY), ProductType.TEMPORARY, 0));

    // Call the getDeliveryDates method on the DeliveryService instance
    List<DeliveryOption> deliveryOptions = deliveryService.getDeliveryOptions("13756", productList);

    // Print out the available delivery dates
    for (DeliveryOption deliveryOption : deliveryOptions) {
      System.out.println("Postal Code: " + deliveryOption.getPostalCode());
      System.out.println("DeliveryOption Date: " + deliveryOption.getDeliveryDate());
      System.out.println("Is Green DeliveryOption: " + deliveryOption.isGreenDelivery());
      System.out.println();
    }
  }
}