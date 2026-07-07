package com.practice.product_service.product;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.practice.product_service.exception.ProductPurchaseException;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

  private final ProductRepository repository;
  private final ProductMapper mapper;

  public Integer createProduct(ProductRequest request) {
    var product = mapper.toProduct(request);
    return repository.save(product).getId();
  }

  public ProductResponse getProductById(Integer productId) {
    return repository.findById(productId).map(mapper::fromProduct)
        .orElseThrow(() -> new EntityNotFoundException("Product not found with this Id :: " + productId));
  }

  public List<ProductResponse> getAllProducts() {
    return repository.findAll()
        .stream().map(mapper::fromProduct)
        .toList();
  }

  public List<ProductPurchaseResponse> purchaseProduct(List<ProductPurchaseRequest> request) {
    var productIds = request.stream()
        .map(ProductPurchaseRequest::productId)
        .toList();
    var storedProducts = repository.findAllByIdInOrderById(productIds);
    if (productIds.size() != storedProducts.size())
      throw new ProductPurchaseException("One or more product does not exists");

    var storedRequest = request.stream()
        .sorted(Comparator.comparing(ProductPurchaseRequest::productId))
        .toList();
    var purchasedProducts = new ArrayList<ProductPurchaseResponse>();
    for (int i = 0; i < storedProducts.size(); i++) {
      var storedProduct = storedProducts.get(i);
      var requestedProduct = storedRequest.get(i);
      if (storedProduct.getAvailableQuantity() < requestedProduct.quantity())
        throw new ProductPurchaseException("Insufficient stock for product with id :: " + requestedProduct.productId());
      var newAvailableQuantity = storedProduct.getAvailableQuantity() - requestedProduct.quantity();
      storedProduct.setAvailableQuantity(newAvailableQuantity);
      repository.save(storedProduct);
      purchasedProducts.add(mapper.toProductPurchaseResponse(storedProduct, requestedProduct.quantity()));
    }
    return purchasedProducts;
  }

}
