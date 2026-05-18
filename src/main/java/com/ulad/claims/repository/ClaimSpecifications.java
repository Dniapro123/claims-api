package com.ulad.claims.repository;

import com.ulad.claims.model.Claim;
import com.ulad.claims.model.ClaimStatus;
import org.springframework.data.jpa.domain.Specification;

// Utility class for building dynamic JPA Specifications for querying Claim entities based on various criteria.
public class ClaimSpecifications {
  
  // Specification to filter claims by their status. If the status is null, it returns a specification that matches all records.
  public static Specification<Claim> hasStatus(ClaimStatus status) {
    return (root, query, cb) -> status == null ? cb.conjunction() : cb.equal(root.get("status"), status);
  }

  private ClaimSpecifications() {}
}