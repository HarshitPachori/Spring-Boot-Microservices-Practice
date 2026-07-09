package com.practice.order_service.orderline;

import com.practice.order_service.order.Order;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class OrderLine {

  @Id
  @GeneratedValue
  private Integer id;
  @JoinColumn(name = "order_id")
  private Order order;
  private Integer productId;
  private double quantity;
}
