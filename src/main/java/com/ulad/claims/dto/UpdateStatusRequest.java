package com.ulad.claims.dto;

import com.ulad.claims.model.ClaimStatus;
import jakarta.validation.constraints.NotNull;

/// DTO for updating the status of an existing claim.
public record UpdateStatusRequest(@NotNull ClaimStatus status) {}
