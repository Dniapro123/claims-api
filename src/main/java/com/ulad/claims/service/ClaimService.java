package com.ulad.claims.service;

import com.ulad.claims.dto.*;
import com.ulad.claims.exception.NotFoundException;
import com.ulad.claims.model.Claim;
import com.ulad.claims.repository.ClaimRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ulad.claims.model.ClaimStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.ulad.claims.exception.BadRequestException;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.web.PageableDefault;

import static com.ulad.claims.repository.ClaimSpecifications.hasStatus;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClaimService {

  private final ClaimRepository repo;

  public ClaimResponse create(CreateClaimRequest req) {
    Claim claim = Claim.builder()
        .title(req.title())
        .description(req.description())
        .amount(req.amount())
        .build();
    Claim saved = repo.save(claim);
    return toResponse(saved);
  }

  public List<ClaimResponse> list() {
    return repo.findAll(Sort.by(Sort.Direction.DESC, "createdAt")).stream()
        .map(this::toResponse)
        .toList();
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

  public Page<ClaimResponse> list(ClaimStatus status, Pageable pageable) {
  Specification<Claim> spec = hasStatus(status);
  return repo.findAll(spec, pageable).map(this::toResponse);
}
}
