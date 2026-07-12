package com.practice.order_service.kafka;

import java.math.BigDecimal;
import java.util.List;

import com.practice.order_service.customer.CustomerResponse;
import com.practice.order_service.order.PaymentMethod;
import com.practice.order_service.product.PurchaseResponse;

public record OrderConfirmation(
    String orderReference,
    BigDecimal totalAmount,
    PaymentMethod paymentMethod,
    CustomerResponse customer,
    List<PurchaseResponse> products) {
}
