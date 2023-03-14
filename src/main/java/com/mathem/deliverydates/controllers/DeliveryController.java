package com.mathem.deliverydates.controllers;

import com.mathem.deliverydates.models.DeliveryOption;
import com.mathem.deliverydates.models.Product;
import com.mathem.deliverydates.services.DeliveryService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class DeliveryController {

  @Autowired
  DeliveryService deliveryService;

  @PostMapping("/deliveryOptions")
  public ResponseEntity<List<DeliveryOption>> getDeliveryOptions(
      @RequestBody List<Product> products,
      @RequestParam String postalCode) {
    List<DeliveryOption> deliveryOptions = deliveryService.getDeliveryOptions(postalCode, products);
    return ResponseEntity.ok(deliveryOptions);
  }
}
