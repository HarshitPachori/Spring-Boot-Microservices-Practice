package com.practice.order_service.order;

import java.util.List;

import org.springframework.stereotype.Service;

import com.practice.order_service.customer.CustomerClient;
import com.practice.order_service.exception.BusinessException;
import com.practice.order_service.kafka.OrderConfirmation;
import com.practice.order_service.kafka.OrderProducer;
import com.practice.order_service.orderline.OrderLineRequest;
import com.practice.order_service.orderline.OrderLineService;
import com.practice.order_service.product.ProductClient;
import com.practice.order_service.product.PurchaseRequest;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {

  private final OrderRepository repository;
  private final CustomerClient customerClient;
  private final ProductClient productClient;
  private final OrderMapper mapper;
  private final OrderLineService orderLineService;
  private final OrderProducer orderProducer;

  public Integer createOrder(OrderRequest request) {
    // check the customer -> customer microservice using open feign
    var customer = customerClient.findCustomerById(request.customerId()).orElseThrow(() -> new BusinessException(
        "Cannot create order :: No customer exists with provided customer id :: " + request.customerId()));

    // purchase the product -> product microservice using rest template just for
    // learning
    var purchasedProduct = productClient.purchaseProducts(request.products());

    // process the order - persist the order
    var order = repository.save(mapper.toOrder(request));
    for (PurchaseRequest purchaseRequest : request.products()) {
      orderLineService.saveOrderLine(
          new OrderLineRequest(null, order.getId(), purchaseRequest.productId(), purchaseRequest.quantity()));
    }
    // persist order lines

    // start payment process -> payment microservice
    // TODO :

    // send order confirmation -> notification service
    orderProducer.sendOrderConfirmation(
        new OrderConfirmation(
            request.reference(),
            request.amount(),
            request.paymentMethod(),
            customer,
            purchasedProduct));

    return order.getId();
  }

  public List<OrderResponse> findAllOrders() {
    return repository.findAll()
        .stream()
        .map(mapper::fromOrder)
        .toList();
  }

  public OrderResponse findOrderById(Integer orderId) {
    return repository.findById(orderId)
        .map(mapper::fromOrder)
        .orElseThrow(() -> new EntityNotFoundException("Order not found with provided order id :: " + orderId));
  }
}
