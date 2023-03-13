package org.mathem.deliverydates.models;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Product {
  private int productId;
  private String name;
  private List<String> deliveryDays;
  private String productType;
  private int daysInAdvance;
}
