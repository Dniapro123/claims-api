package com.ulad.claims.dto;

import java.time.Instant;

/// DTO for representing customer details in API responses.
public record CustomerResponse(
        Long id,
        String firstName,
        String lastName,
        String email,
        String phone,
        Instant createdAt
) {
}