package com.practice.product_service.product;

import jakarta.validation.constraints.NotBlank;

public record ProductPurchaseRequest(
    @NotBlank(message = "Product id is required") Integer productId,
    @NotBlank(message = "product quantity is required") Integer quantity) {
}
