package org.mathem.deliverydates.models;

import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DeliveryOption {
  private int postalCode;
  private LocalDate deliveryDate;
  private boolean isGreenDelivery;
}
