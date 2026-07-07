package com.practice.product_service.product;

import org.springframework.stereotype.Service;

import com.practice.product_service.category.Category;

@Service
public class ProductMapper {

  public Product toProduct(ProductRequest request) {
    return Product.builder()
        .id(request.id())
        .name(request.name())
        .description(request.description())
        .price(request.price())
        .availableQuantity(request.availableQuantity())
        .category(Category.builder().id(request.categoryId()).build())
        .build();
  }

  public ProductResponse fromProduct(Product product) {
    return new ProductResponse(
        product.getId(),
        product.getName(),
        product.getDescription(),
        product.getAvailableQuantity(),
        product.getPrice(),
        product.getCategory().getId(),
        product.getCategory().getName(),
        product.getCategory().getDescription());
  }

  public ProductPurchaseResponse toProductPurchaseResponse(Product storedProduct,
      Integer quantity) {
    return new ProductPurchaseResponse(
        storedProduct.getId(),
        storedProduct.getName(),
        storedProduct.getDescription(),
        storedProduct.getPrice(),
        quantity);
  }

}
