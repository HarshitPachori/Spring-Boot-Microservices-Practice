package com.practice.customer_service.customer;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

  private final CustomerService service;

  @PostMapping
  public ResponseEntity<String> createCustomer(@RequestBody @Valid CustomerRequest request) {
    return ResponseEntity.ok(service.createCustomer(request));
  }

  @PutMapping
  public ResponseEntity<Void> updateCustomer(@RequestBody @Valid CustomerRequest request) {
    service.updateCustomer(request);
    return ResponseEntity.accepted().build();
  }

  @GetMapping
  public ResponseEntity<List<CustomerResponse>> getAllCustomers() {
    var allCustomers = service.getAllCustomers();
    return ResponseEntity.ok(allCustomers);
  }

  @GetMapping("/{id}")
  public ResponseEntity<CustomerResponse> getCustomerById(@PathVariable("id") String id) {
    var customer = service.getCustomerById(id);
    return ResponseEntity.ok(customer);
  }

  @GetMapping("/exists/{id}")
  public ResponseEntity<Boolean> customerExistsById(@PathVariable("id") String id) {
    var exists = service.customerExistsById(id);
    return ResponseEntity.ok(exists);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteCustomerById(@PathVariable("id") String id) {
    service.deleteCustomerById(id);
    return ResponseEntity.noContent().build();
  }

}
