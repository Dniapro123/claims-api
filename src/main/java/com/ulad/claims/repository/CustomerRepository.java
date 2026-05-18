package com.ulad.claims.repository;

import com.ulad.claims.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// Repository interface for managing Customer entities.
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    // Finds a customer by their email address. Returns an Optional that may be empty if no customer is found with the given email.
    Optional<Customer> findByEmail(String email);

    boolean existsByEmail(String email);
}