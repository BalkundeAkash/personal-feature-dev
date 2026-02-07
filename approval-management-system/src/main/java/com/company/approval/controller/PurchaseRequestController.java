package com.company.approval.controller;

import com.company.approval.entity.PurchaseRequest;
import com.company.approval.service.PurchaseRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/requests")
@RequiredArgsConstructor
public class PurchaseRequestController {

    private final PurchaseRequestService service;

    // Rajesh creates request
    @PostMapping("/create/{userId}")
    public PurchaseRequest createRequest(
            @PathVariable Long userId,
            @RequestBody PurchaseRequest request) {

        return service.createRequest(userId, request);
    }

    // Amit approves
    @PutMapping("/{requestId}/approve/{approverId}")
    public PurchaseRequest approve(
            @PathVariable Long requestId,
            @PathVariable Long approverId,
            @RequestParam String remarks) {

        return service.approveRequest(requestId, approverId, remarks);
    }

    // Amit rejects
    @PutMapping("/{requestId}/reject/{approverId}")
    public PurchaseRequest reject(
            @PathVariable Long requestId,
            @PathVariable Long approverId,
            @RequestParam String remarks) {

        return service.rejectRequest(requestId, approverId, remarks);
    }

    // Manager dashboard
    @GetMapping("/pending")
    public List<PurchaseRequest> pendingRequests() {
        return service.getPendingRequests();
    }
}
