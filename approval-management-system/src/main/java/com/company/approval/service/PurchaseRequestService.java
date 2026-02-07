package com.company.approval.service;

import com.company.approval.entity.PurchaseRequest;
import com.company.approval.entity.User;
import com.company.approval.enums.RequestStatus;
import com.company.approval.exception.ResourceNotFoundException;
import com.company.approval.repository.PurchaseRequestRepository;
import com.company.approval.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PurchaseRequestService {

    private final PurchaseRequestRepository requestRepository;
    private final UserRepository userRepository;

    // Rajesh creates request
    public PurchaseRequest createRequest(Long userId, PurchaseRequest request) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        request.setRequestedBy(user);
        request.setStatus(RequestStatus.PENDING);

        return requestRepository.save(request);
    }

    // Amit approves
    public PurchaseRequest approveRequest(Long requestId, Long approverId, String remarks) {

        PurchaseRequest request = requestRepository.findById(requestId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Request not found"));

        User approver = userRepository.findById(approverId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Approver not found"));

        request.setStatus(RequestStatus.APPROVED);
        request.setApprovedBy(approver);
        request.setRemarks(remarks);

        return requestRepository.save(request);
    }

    // Amit rejects
    public PurchaseRequest rejectRequest(Long requestId, Long approverId, String remarks) {

        PurchaseRequest request = requestRepository.findById(requestId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Request not found"));

        User approver = userRepository.findById(approverId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Approver not found"));

        request.setStatus(RequestStatus.REJECTED);
        request.setApprovedBy(approver);
        request.setRemarks(remarks);

        return requestRepository.save(request);
    }

    public List<PurchaseRequest> getPendingRequests() {
        return requestRepository.findByStatus(RequestStatus.PENDING);
    }
}
