package com.practice.product_service.product;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

  private final ProductService service;

  @PostMapping
  public ResponseEntity<Integer> createProduct(@RequestBody @Valid ProductRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED).body(service.createProduct(request));
  }

  @GetMapping("/{product-id}")
  public ResponseEntity<ProductResponse> getProductById(@PathVariable("product-id") Integer productId) {
    return ResponseEntity.ok().body(service.getProductById(productId));
  }

  @GetMapping
  public ResponseEntity<List<ProductResponse>> getAllProducts() {
    return ResponseEntity.ok().body(service.getAllProducts());
  }

  @PostMapping("/purchase")
  public ResponseEntity<List<ProductPurchaseResponse>> purchaseProduct(
      @RequestBody List<ProductPurchaseRequest> request) {
    return ResponseEntity.ok().body(service.purchaseProduct(request));
  }
}
