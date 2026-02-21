package com.ulad.claims.controller;

import com.ulad.claims.dto.*;
import com.ulad.claims.service.ClaimService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import com.ulad.claims.model.ClaimStatus;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

import com.ulad.claims.model.ClaimStatus;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/claims")
@RequiredArgsConstructor
public class ClaimController {

  private final ClaimService service;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ClaimResponse create(@Valid @RequestBody CreateClaimRequest req) {
    return service.create(req);
  }

  @GetMapping
  public Page<ClaimResponse> list(
      @RequestParam(required = false) ClaimStatus status,
      @ParameterObject
      @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC)
      Pageable pageable
  ) {
    return service.list(status, pageable);
  }

  @GetMapping("/{id}")
  public ClaimResponse get(@PathVariable Long id) {
    return service.get(id);
  }

  @PutMapping("/{id}/status")
  public ClaimResponse updateStatus(@PathVariable Long id, @Valid @RequestBody UpdateStatusRequest req) {
    return service.updateStatus(id, req);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable Long id) {
    service.delete(id);
  }
}
