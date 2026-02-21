package com.ulad.claims.dto;

import com.ulad.claims.model.ClaimStatus;

import java.math.BigDecimal;
import java.time.Instant;

public record ClaimResponse(
    Long id,
    String title,
    String description,
    BigDecimal amount,
    ClaimStatus status,
    Instant createdAt
) {}

