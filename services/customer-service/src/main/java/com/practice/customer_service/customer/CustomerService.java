package com.practice.customer_service.customer;

import java.util.List;

import org.springframework.stereotype.Service;

import com.practice.customer_service.exception.CustomerNotFoundException;

import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerService {

  private final CustomerRepository repository;
  private final CustomerMapper mapper;

  public String createCustomer(CustomerRequest request) {
    var customer = repository.save(mapper.toCustomer(request));
    return customer.getId();
  }

  public void updateCustomer(CustomerRequest request) {
    var customer = repository.findById(request.id())
        .orElseThrow(() -> new CustomerNotFoundException(String.format("Customer with ID %s not found", request.id())));
    mergeCustomer(customer, request);
    repository.save(customer);
  }

  private void mergeCustomer(Customer customer, CustomerRequest request) {
    if (StringUtils.isNotBlank(request.firstName()))
      customer.setFirstName(request.firstName());
    if (StringUtils.isNotBlank(request.lastName()))
      customer.setLastName(request.lastName());
    if (StringUtils.isNotBlank(request.email()))
      customer.setEmail(request.email());
    if (request.address() != null)
      customer.setAddress(request.address());
  }

  public List<CustomerResponse> getAllCustomers() {
    return repository
        .findAll()
        .stream()
        .map(mapper::fromCustomer)
        .toList();
  }

  public CustomerResponse getCustomerById(String id) {
    var customer = repository.findById(id)
        .orElseThrow(() -> new CustomerNotFoundException(String.format("Customer with ID %s not found", id)));
    return mapper.fromCustomer(customer);
  }

  public Boolean customerExistsById(String id) {
    return repository.findById(id).isPresent();
  }

  public void deleteCustomerById(String id) {
    var customer = repository.findById(id)
        .orElseThrow(() -> new CustomerNotFoundException(String.format("Customer with ID %s not found", id)));
    repository.delete(customer);
  }

}
