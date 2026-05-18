package com.ulad.claims.service;

import com.ulad.claims.dto.CustomerRequest;
import com.ulad.claims.dto.CustomerResponse;
import com.ulad.claims.exception.BadRequestException;
import com.ulad.claims.exception.NotFoundException;
import com.ulad.claims.model.Customer;
import com.ulad.claims.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository repo;

    public CustomerResponse create(CustomerRequest req) {
        if (repo.existsByEmail(req.email())) {
            throw new BadRequestException("Customer with email already exists: " + req.email());
        }

        Customer customer = Customer.builder()
                .firstName(req.firstName())
                .lastName(req.lastName())
                .email(req.email())
                .phone(req.phone())
                .build();

        return toResponse(repo.save(customer));
    }

    public Page<CustomerResponse> list(Pageable pageable) {
        return repo.findAll(pageable)
                .map(this::toResponse);
    }

    public CustomerResponse get(Long id) {
        return toResponse(find(id));
    }

    public CustomerResponse update(Long id, CustomerRequest req) {
        Customer customer = find(id);

        repo.findByEmail(req.email())
                .filter(existing -> !existing.getId().equals(id))
                .ifPresent(existing -> {
                    throw new BadRequestException("Customer with email already exists: " + req.email());
                });

        customer.setFirstName(req.firstName());
        customer.setLastName(req.lastName());
        customer.setEmail(req.email());
        customer.setPhone(req.phone());

        return toResponse(repo.save(customer));
    }

    public void delete(Long id) {
        Customer customer = find(id);
        repo.delete(customer);
    }

    private Customer find(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Customer not found: " + id));
    }

    private CustomerResponse toResponse(Customer c) {
        return new CustomerResponse(
                c.getId(),
                c.getFirstName(),
                c.getLastName(),
                c.getEmail(),
                c.getPhone(),
                c.getCreatedAt()
        );
    }
}