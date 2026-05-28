package com.company.leavemanagement.service;

import com.company.leavemanagement.dto.LeaveResponse;
import com.company.leavemanagement.entity.Employee;
import com.company.leavemanagement.entity.LeaveRequest;
import com.company.leavemanagement.enums.Role;
import com.company.leavemanagement.repository.EmployeeRepository;
import com.company.leavemanagement.repository.LeaveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetLeaveService {

    private final LeaveRepository leaveRepository;
    private final EmployeeRepository employeeRepository;

    public Page<LeaveResponse> getEmployeeLeaves(
            Long employeeId,
            int page,
            int size
    ) {

        return leaveRepository
                .findByEmployeeId(
                        employeeId,
                        PageRequest.of(page, size)
                )
                .map(this::mapToResponse);
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

    public Page<LeaveResponse> getLeavesByRole(
            Long employeeId,
            int page,
            int size
    ) {

        Employee manager = employeeRepository.findById(employeeId)
                .orElseThrow(() ->
                        new RuntimeException("Employee not found"));

        if (manager.getRole() != Role.MANAGER) {

            return getEmployeeLeaves(
                    employeeId,
                    page,
                    size
            );
        }

        return leaveRepository
                .findAll(PageRequest.of(page, size))
                .map(this::mapToResponse);
    }
}