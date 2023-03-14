package com.mathem.deliverydates.services;

import static com.mathem.deliverydates.utils.DeliveryUtils.getGreenDeliveryDates;
import static com.mathem.deliverydates.utils.DeliveryUtils.isDeliveryDateValid;
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

  /*
  - A delivery date is not valid if a product can't be delivered on that weekday.
  - A delivery date is not valid if the customer has ordered a product that cannot be delivered in time.
  This is determined by the daysInAdvance property on the product. I.E. if the product needs to be
  ordered 4 days in advance, all delivery days before today + 4 are invalid as the product cannot be
  delivered on time.
  - All external products need to be ordered 5 days in advance.
  - Temporary products can only be ordered within the current week (Mon-Sun)
   */

  public List<DeliveryOption> getDeliveryOptions(String postalCode, List<Product> products) {
    List<DeliveryOption> deliveryOptions = new ArrayList<>();
    LocalDate today = LocalDate.now();
    LocalDate twoWeeksLater = today.plusWeeks(2);
    List<LocalDate> greenDeliveryDates = getGreenDeliveryDates(today, twoWeeksLater);

    for (LocalDate date = today; !date.isAfter(twoWeeksLater); date = date.plusDays(1)) {
      if (isDeliveryWeekdayValid(date, products) && isDeliveryDateValid(LocalDate.now(), date,
          products) && isDeliveryDateValidForTemporaryProducts(LocalDate.now(), date,
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