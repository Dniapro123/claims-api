package com.ulad.claims.dto;

import com.ulad.claims.model.ClaimStatus;

import java.math.BigDecimal;
import java.time.Instant;

// DTO for representing claim details in API responses. 
// Contains claim information such as title,
//  description, amount, status, and creation timestamp.
public record ClaimResponse(
    Long id,
    String title,
    String description,
    BigDecimal amount,
    ClaimStatus status,
    Instant createdAt
) {}

