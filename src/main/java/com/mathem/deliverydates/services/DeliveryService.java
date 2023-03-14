package com.mathem.deliverydates.services;

import static com.mathem.deliverydates.utils.DeliveryUtils.getGreenDeliveryDates;
import static com.mathem.deliverydates.utils.DeliveryUtils.isDeliveryDateValid;
import static com.mathem.deliverydates.utils.DeliveryUtils.isDeliveryDateValidForExternalProducts;
import static com.mathem.deliverydates.utils.DeliveryUtils.isDeliveryDateValidForTemporaryProducts;
import static com.mathem.deliverydates.utils.DeliveryUtils.isDeliveryWeekdayValid;

import com.mathem.deliverydates.models.DeliveryOption;
import com.mathem.deliverydates.models.Product;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class DeliveryService {
  public List<DeliveryOption> getDeliveryOptions(String postalCode, List<Product> products) {
    List<DeliveryOption> deliveryOptions = new ArrayList<>();
    LocalDate today = LocalDate.now();
    LocalDate twoWeeksLater = today.plusWeeks(2);
    List<LocalDate> greenDeliveryDates = getGreenDeliveryDates(today, twoWeeksLater);

    for (LocalDate date = today; !date.isAfter(twoWeeksLater); date = date.plusDays(1)) {
      if (isDeliveryWeekdayValid(date, products) && isDeliveryDateValid(LocalDate.now(), date,
          products) && isDeliveryDateValidForTemporaryProducts(LocalDate.now(), date,
          products)  && isDeliveryDateValidForExternalProducts(LocalDate.now(), date,
          products)) {
        DeliveryOption deliveryOption = new DeliveryOption();
        deliveryOption.setPostalCode(postalCode);
        deliveryOption.setDeliveryDate(date);
        deliveryOption.setGreenDelivery(greenDeliveryDates.contains(date));
        deliveryOptions.add(deliveryOption);
      }
    }

    deliveryOptions.sort(Comparator.comparing(DeliveryOption::isGreenDelivery).reversed()
        .thenComparing(DeliveryOption::getDeliveryDate));
    return deliveryOptions;
  }
}