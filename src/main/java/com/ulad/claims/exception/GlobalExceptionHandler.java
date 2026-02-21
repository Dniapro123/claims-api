package com.ulad.claims.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<ApiError> handleNotFound(NotFoundException ex, HttpServletRequest req) {
    return build(HttpStatus.NOT_FOUND, ex.getMessage(), req.getRequestURI(), null);
  }

    @ExceptionHandler(BadRequestException.class)
  public ResponseEntity<ApiError> handleBadRequest(BadRequestException ex, HttpServletRequest req) {
    return build(HttpStatus.BAD_REQUEST, ex.getMessage(), req.getRequestURI(), null);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest req) {
    List<ApiError.FieldErrorItem> fields = ex.getBindingResult().getFieldErrors().stream()
        .map(this::mapFieldError)
        .toList();
    return build(HttpStatus.BAD_REQUEST, "Validation failed", req.getRequestURI(), fields);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiError> handleGeneric(Exception ex, HttpServletRequest req) {
    return build(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error", req.getRequestURI(), null);
  }

  private ApiError.FieldErrorItem mapFieldError(FieldError fe) {
    return new ApiError.FieldErrorItem(fe.getField(), fe.getDefaultMessage());
  }

  private ResponseEntity<ApiError> build(HttpStatus status, String message, String path,
                                         List<ApiError.FieldErrorItem> fields) {
    ApiError body = new ApiError(
        Instant.now(),
        status.value(),
        status.getReasonPhrase(),
        message,
        path,
        fields
    );
    return ResponseEntity.status(status).body(body);
  }
}
