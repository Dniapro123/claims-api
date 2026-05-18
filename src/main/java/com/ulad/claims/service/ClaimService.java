package com.ulad.claims.service;

import com.ulad.claims.dto.ClaimResponse;
import com.ulad.claims.dto.CreateClaimRequest;
import com.ulad.claims.dto.UpdateStatusRequest;
import com.ulad.claims.exception.BadRequestException;
import com.ulad.claims.exception.NotFoundException;
import com.ulad.claims.model.Claim;
import com.ulad.claims.model.ClaimStatus;
import com.ulad.claims.model.Customer;
import com.ulad.claims.repository.ClaimRepository;
import com.ulad.claims.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.ulad.claims.repository.ClaimSpecifications.hasStatus;

@Service
@RequiredArgsConstructor
public class ClaimService {

    private final ClaimRepository repo;
    private final CustomerRepository customerRepository;

    public ClaimResponse create(CreateClaimRequest req) {
        Customer customer = customerRepository.findById(req.customerId())
                .orElseThrow(() -> new NotFoundException("Customer not found: " + req.customerId()));

        Claim claim = Claim.builder()
                .title(req.title())
                .description(req.description())
                .amount(req.amount())
                .customer(customer)
                .build();

        Claim saved = repo.save(claim);
        return toResponse(saved);
    }

    public List<ClaimResponse> list() {
        return repo.findAll(Sort.by(Sort.Direction.DESC, "createdAt")).stream()
                .map(this::toResponse)
                .toList();
    }

    public Page<ClaimResponse> list(ClaimStatus status, Pageable pageable) {
        Specification<Claim> spec = hasStatus(status);
        return repo.findAll(spec, pageable).map(this::toResponse);
    }

    public ClaimResponse get(Long id) {
        return toResponse(find(id));
    }

    public ClaimResponse updateStatus(Long id, UpdateStatusRequest req) {
        Claim claim = find(id);

        var current = claim.getStatus();
        var next = req.status();

        if (current != null && next != null && !current.canTransitionTo(next)) {
            throw new BadRequestException("Invalid status transition: " + current + " -> " + next);
        }

        claim.setStatus(next);
        return toResponse(repo.save(claim));
    }

    public void delete(Long id) {
        Claim claim = find(id);
        repo.delete(claim);
    }

    private Claim find(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Claim not found: " + id));
    }

    private ClaimResponse toResponse(Claim c) {
        return new ClaimResponse(
                c.getId(),
                c.getTitle(),
                c.getDescription(),
                c.getAmount(),
                c.getStatus(),
                c.getCreatedAt()
        );
    }
}