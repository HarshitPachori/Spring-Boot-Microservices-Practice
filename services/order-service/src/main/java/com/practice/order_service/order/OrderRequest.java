package com.practice.order_service.order;

import java.math.BigDecimal;
import java.util.List;

import com.practice.order_service.product.PurchaseRequest;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;

public record OrderRequest(
  Integer id,
  String reference,
  @Positive(message = "Order amount should be positive")
  BigDecimal amount,
  @NotBlank(message = "Payment method should be precised")
  PaymentMethod paymentMethod,
  @NotBlank(message = "Customer should be present")
  @NotEmpty(message = "Customer should be present")
  String customerId,
  @NotEmpty(message = "You should atleast purchase one product")
  List<PurchaseRequest> products
) {
}
