package com.ulad.claims.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "claims")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Claim {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 200)
  private String title;

  @Column(nullable = false, length = 2000)
  private String description;

  @Column(nullable = false)
  private BigDecimal amount;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 30)
  private ClaimStatus status;

  @Column(nullable = false, updatable = false)
  private Instant createdAt;

  @PrePersist
  void onCreate() {
    if (status == null) status = ClaimStatus.NEW;
    createdAt = Instant.now();
  }
}
