package com.ulad.claims.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
/// DTO for creating a new claim. 
/// Includes fields for claim title, 
// description, amount, and associated customer ID.
public record CreateClaimRequest(
    @NotBlank @Size(max = 200) String title,
    @NotBlank @Size(max = 2000) String description,
    @NotNull @DecimalMin(value = "0.01") BigDecimal amount,
    @NotNull Long customerId
) {}