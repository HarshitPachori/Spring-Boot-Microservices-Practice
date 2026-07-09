package com.practice.order_service.orderline;

import org.springframework.stereotype.Service;

import com.practice.order_service.order.Order;

@Service
public class OrderLineMapper {

  public OrderLine toOrderLine(OrderLineRequest orderLineRequest) {
    return OrderLine.builder()
        .id(orderLineRequest.id())
        .quantity(orderLineRequest.quantity())
        .order(Order.builder().id(orderLineRequest.orderId()).build())
        .productId(orderLineRequest.productId())
        .build();
  }

}
