package com.ulad.claims.dto;

import com.ulad.claims.model.ClaimStatus;
import jakarta.validation.constraints.NotNull;

public record UpdateStatusRequest(@NotNull ClaimStatus status) {}
