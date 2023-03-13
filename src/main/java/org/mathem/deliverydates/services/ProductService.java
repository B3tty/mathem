package org.mathem.deliverydates.services;

import java.util.ArrayList;
import java.util.List;
import org.mathem.deliverydates.models.Product;
import org.mathem.deliverydates.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
  @Autowired
  ProductRepository productRepository;


  public List<Product> getAllProducts() {
    List<Product> product = new ArrayList<>();
    productRepository.findAll().forEach(message -> product.add(message));
    return (product);
  }

  public Product getProductById(Long productId) {
    var product = productRepository.findById(productId);
    if (!product.isEmpty()) {
      return product.get();
    } else {
      return null;
    }
  }
}
