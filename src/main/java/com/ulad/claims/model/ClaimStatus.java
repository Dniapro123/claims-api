package com.ulad.claims.model;

// Allowed transitions:
// NEW -> IN_REVIEW / REJECTED
// IN_REVIEW -> APPROVED / REJECTED
// APPROVED / REJECTED are terminal states

public enum ClaimStatus {
  NEW,
  IN_REVIEW,
  APPROVED,
  REJECTED;

  public boolean canTransitionTo(ClaimStatus next) {
    if (next == null) return false;
    return switch (this) {
      case NEW -> next == IN_REVIEW || next == REJECTED;
      case IN_REVIEW -> next == APPROVED || next == REJECTED;
      case APPROVED, REJECTED -> false;
    };
  }
}
