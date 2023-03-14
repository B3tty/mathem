package com.mathem.deliverydates.models;

import java.time.DayOfWeek;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {
  private int productId;
  private String name;
  private List<DayOfWeek> deliveryDays;
  private ProductType productType;
  private int daysInAdvance;

  public static enum ProductType{
    NORMAL,
    EXTERNAL,
    TEMPORARY
  }
}
