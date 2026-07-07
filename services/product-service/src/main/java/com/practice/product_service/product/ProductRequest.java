package com.practice.product_service.product;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record ProductRequest(Integer id,
    @NotBlank(message = "Product name is required") String name,
    @NotBlank(message = "Product description is required") String description,
    @Positive(message = "Product available quantity must be positive") double availableQuantity,
    @Positive(message = "Product price must be positive") BigDecimal price,
    Integer categoryId) {
}
