package com.ulad.claims.exception;

import java.time.Instant;
import java.util.List;

/// DTO for representing API error details in responses.
public record ApiError(
    Instant timestamp,
    int status,
    String error,
    String message,
    String path,
    List<FieldErrorItem> fieldErrors
) {
  public record FieldErrorItem(String field, String message) {}
}
