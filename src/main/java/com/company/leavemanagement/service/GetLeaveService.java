package com.company.leavemanagement.service;

import com.company.leavemanagement.dto.LeaveResponse;
import com.company.leavemanagement.entity.LeaveRequest;
import com.company.leavemanagement.repository.LeaveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetLeaveService {

    private final LeaveRepository leaveRepository;

    public List<LeaveResponse> getEmployeeLeaves(Long employeeId) {

        List<LeaveRequest> leaves =
                leaveRepository.findByEmployeeId(employeeId);

        return leaves.stream()
                .map(this::mapToResponse)
                .toList();
    }

    public Page<LeaveResponse> getAllLeaves(int page, int size) {

        return leaveRepository
                .findAll(PageRequest.of(page, size))
                .map(this::mapToResponse);
    }

    private LeaveResponse mapToResponse(
            LeaveRequest leave
    ) {
        return new LeaveResponse(
                leave.getId(),
                leave.getEmployee().getId(),
                leave.getEmployee().getName(),
                leave.getStartDate(),
                leave.getEndDate(),
                leave.getReason(),
                leave.getStatus()
        );
    }
}