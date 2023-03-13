package org.mathem.deliverydates.services;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.mathem.deliverydates.models.DeliveryOption;
import org.mathem.deliverydates.models.Product;
import org.springframework.stereotype.Service;

@Service
public class DeliveryService {

  public List<DeliveryOption> getDeliveryOptions(String postalCode, List<Product> products) {
    List<DeliveryOption> deliveryOptions = new ArrayList<>();
    LocalDate today = LocalDate.now();
    LocalDate twoWeeksLater = today.plusWeeks(2);
/*    List<LocalDate> greenDeliveryDates = getGreenDeliveryDates(today, twoWeeksLater);

    for (LocalDate date = today; !date.isAfter(twoWeeksLater); date = date.plusDays(1)) {
      if (isDeliveryDayValid(date, products) && isDeliveryDateValid(date, products)) {
        DeliveryOption deliveryOption = new DeliveryOption();
        deliveryOption.setPostalCode(postalCode);
        deliveryOption.setDeliveryDate(formatDeliveryDate(date));
        deliveryOption.setGreenDelivery(greenDeliveryDates.contains(date));
        deliveryOptions.add(deliveryOption);
      }
    }

    deliveryOptions.sort(Comparator.comparing(DeliveryOption::getIsGreenDelivery).reversed()
        .thenComparing(DeliveryOption::getDeliveryDate));*/
    return deliveryOptions;
  }


}