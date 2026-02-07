package com.company.approval.entity;

import com.company.approval.enums.RequestStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "purchase_requests")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false)
    private Double amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RequestStatus status;

    // Rajesh (who created request)
    @ManyToOne
    @JoinColumn(name = "requested_by", nullable = false)
    private User requestedBy;

    // Amit (who approved/rejected)
    @ManyToOne
    @JoinColumn(name = "approved_by")
    private User approvedBy;

    private String remarks;

    private LocalDateTime createdAt = LocalDateTime.now();
}
