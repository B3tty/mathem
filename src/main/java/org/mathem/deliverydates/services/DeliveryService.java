package org.mathem.deliverydates.services;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.mathem.deliverydates.models.DeliveryOption;
import org.mathem.deliverydates.models.Product;
import org.mathem.deliverydates.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeliveryService {

  public List<DeliveryOption> getDeliveryOptions(String postalCode, List<Product> products) {
    List<DeliveryOption> deliveryOptions = new ArrayList<>();
    LocalDate today = LocalDate.now();
    LocalDate twoWeeksLater = today.plusWeeks(2);
    return deliveryOptions;
  }


}