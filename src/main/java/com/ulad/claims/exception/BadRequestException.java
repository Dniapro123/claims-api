package com.ulad.claims.exception;

// Custom exception for handling bad request scenarios in the API.
public class BadRequestException extends RuntimeException {
  public BadRequestException(String message) {
    super(message);
  }
}