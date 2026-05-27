package com.company.leavemanagement.dto;

import com.company.leavemanagement.enums.LeaveStatus;

import java.time.LocalDate;

public record LeaveResponse(

        Long id,
        Long employeeId,
        String employeeName,
        LocalDate startDate,
        LocalDate endDate,
        String reason,
        LeaveStatus status
) {
}