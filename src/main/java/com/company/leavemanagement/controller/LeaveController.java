package com.company.leavemanagement.controller;

import com.company.leavemanagement.dto.CreateLeaveRequest;
import com.company.leavemanagement.dto.LeaveResponse;
import com.company.leavemanagement.service.ApproveLeaveService;
import com.company.leavemanagement.service.CreateLeaveService;
import com.company.leavemanagement.service.GetLeaveService;
import com.company.leavemanagement.service.RejectLeaveService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/leaves")
@RequiredArgsConstructor
@Tag(name = "Leave Management", description = "Leave management APIs")
public class LeaveController {

    private final CreateLeaveService createLeaveService;
    private final GetLeaveService getLeaveService;
    private final ApproveLeaveService approveLeaveService;
    private final RejectLeaveService rejectLeaveService;

    @Operation(summary = "Create leave request")
    @PostMapping
    public LeaveResponse createLeave(
            @Valid @RequestBody CreateLeaveRequest request
    ) {
        return createLeaveService.execute(request);
    }

    @Operation(summary = "Get employee leave history")
    @GetMapping("/my")
    public Page<LeaveResponse> getMyLeaves(
            @RequestParam Long employeeId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return getLeaveService.getEmployeeLeaves(
                employeeId,
                page,
                size
        );
    }

    @Operation(summary = "Get all leave requests")
    @GetMapping
    public Page<LeaveResponse> getAllLeaves(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return getLeaveService.getAllLeaves(page, size);
    }

    @Operation(summary = "Approve leave request")
    @PutMapping("/{id}/approve")
    public LeaveResponse approveLeave(
            @PathVariable Long id,
            @RequestParam Long managerId
    ) {
        return approveLeaveService.execute(id, managerId);
    }

    @Operation(summary = "Reject leave request")
    @PutMapping("/{id}/reject")
    public LeaveResponse rejectLeave(
            @PathVariable Long id,
            @RequestParam Long managerId
    ) {
        return rejectLeaveService.execute(id, managerId);
    }

    @Operation(summary = "Get leave requests by role")
    @GetMapping("/by-role")
    public Page<LeaveResponse> getLeavesByRole(
            @RequestParam Long managerId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return getLeaveService.getLeavesByRole(
                managerId,
                page,
                size
        );
    }
}