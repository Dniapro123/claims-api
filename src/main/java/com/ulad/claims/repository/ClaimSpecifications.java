package com.ulad.claims.repository;

import com.ulad.claims.model.Claim;
import com.ulad.claims.model.ClaimStatus;
import org.springframework.data.jpa.domain.Specification;

public class ClaimSpecifications {

  public static Specification<Claim> hasStatus(ClaimStatus status) {
    return (root, query, cb) -> status == null ? cb.conjunction() : cb.equal(root.get("status"), status);
  }

  private ClaimSpecifications() {}
}