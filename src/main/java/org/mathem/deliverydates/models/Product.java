package org.mathem.deliverydates.models;

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
  private long productId;
  private String name;
  private List<String> deliveryDays;
  private String productType;
  private int daysInAdvance;
}
