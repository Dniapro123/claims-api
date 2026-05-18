package com.ulad.claims.repository;

import com.ulad.claims.model.Claim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


    // Repository interface for managing Claim entities.
    // Extends JpaRepository for basic CRUD operations and JpaSpecificationExecutor for dynamic query capabilities.
public interface ClaimRepository extends JpaRepository<Claim, Long>, JpaSpecificationExecutor<Claim> {}