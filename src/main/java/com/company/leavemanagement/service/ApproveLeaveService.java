package com.company.leavemanagement.service;

import com.company.leavemanagement.dto.LeaveResponse;
import com.company.leavemanagement.entity.Employee;
import com.company.leavemanagement.entity.LeaveRequest;
import com.company.leavemanagement.enums.LeaveStatus;
import com.company.leavemanagement.enums.Role;
import com.company.leavemanagement.repository.EmployeeRepository;
import com.company.leavemanagement.repository.LeaveRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApproveLeaveService {

    private final LeaveRepository leaveRepository;
    private final EmployeeRepository employeeRepository;

    @Transactional
    public LeaveResponse execute(
            Long leaveId,
            Long managerId
    ) {

        Employee manager = employeeRepository.findById(managerId)
                .orElseThrow(() ->
                        new RuntimeException("Manager not found"));

        if (manager.getRole() != Role.MANAGER) {
            throw new RuntimeException(
                    "Only manager can approve leave");
        }

        LeaveRequest leave = leaveRepository.findById(leaveId)
                .orElseThrow(() ->
                        new RuntimeException("Leave not found"));

        if (leave.getStatus() != LeaveStatus.PENDING) {
            throw new RuntimeException(
                    "Only pending leave can be approved");
        }

        leave.setStatus(LeaveStatus.APPROVED);

        leaveRepository.save(leave);

        return mapToResponse(leave);
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