package com.practice.customer_service.customer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CustomerRequest(
                String id,
                @NotBlank(message = "First name is required") String firstName,
                @NotBlank(message = "Last name is required") String lastName,
                @NotBlank(message = "Email is required") @Email(message = "Email should be valid") String email,
                @NotBlank(message = "Address is required") Address address) {

}
