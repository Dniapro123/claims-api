package com.ulad.claims.dto;

import java.time.Instant;

public record CustomerResponse(
        Long id,
        String firstName,
        String lastName,
        String email,
        String phone,
        Instant createdAt
) {
}