package com.practice.order_service.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record PurchaseRequest(
    @NotBlank(message = "Product is mandatory") Integer productId,
    @Positive(message = "Quantity is mandatory") double quantity) {
}
