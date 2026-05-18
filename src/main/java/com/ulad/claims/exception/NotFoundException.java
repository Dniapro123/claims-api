package com.ulad.claims.exception;

// Custom exception for handling not found scenarios in the API.
public class NotFoundException extends RuntimeException {
  public NotFoundException(String message) {
    super(message);
  }
}
