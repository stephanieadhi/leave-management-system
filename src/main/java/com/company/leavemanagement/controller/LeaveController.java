package com.company.leavemanagement.controller;

import com.company.leavemanagement.dto.CreateLeaveRequest;
import com.company.leavemanagement.dto.LeaveResponse;
import com.company.leavemanagement.service.ApproveLeaveService;
import com.company.leavemanagement.service.CreateLeaveService;
import com.company.leavemanagement.service.GetLeaveService;
import com.company.leavemanagement.service.RejectLeaveService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leaves")
@RequiredArgsConstructor
public class LeaveController {

    private final CreateLeaveService createLeaveService;
    private final GetLeaveService getLeaveService;
    private final ApproveLeaveService approveLeaveService;
    private final RejectLeaveService rejectLeaveService;

    @PostMapping
    public LeaveResponse createLeave(
            @Valid @RequestBody CreateLeaveRequest request
    ) {
        return createLeaveService.execute(request);
    }

    @GetMapping("/my")
    public List<LeaveResponse> getMyLeaves(
            @RequestParam Long employeeId
    ) {
        return getLeaveService.getEmployeeLeaves(employeeId);
    }

    @GetMapping
    public Page<LeaveResponse> getAllLeaves(
            @RequestParam int page,
            @RequestParam int size
    ) {
        return getLeaveService.getAllLeaves(page, size);
    }

    @PutMapping("/{id}/approve")
    public LeaveResponse approveLeave(
            @PathVariable Long id,
            @RequestParam Long managerId
    ) {
        return approveLeaveService.execute(id, managerId);
    }

    @PutMapping("/{id}/reject")
    public LeaveResponse rejectLeave(
            @PathVariable Long id,
            @RequestParam Long managerId
    ) {
        return rejectLeaveService.execute(id, managerId);
    }
}