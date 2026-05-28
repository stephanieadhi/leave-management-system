package com.company.leavemanagement.repository;

import com.company.leavemanagement.entity.LeaveRequest;
import com.company.leavemanagement.enums.LeaveStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LeaveRepository
        extends JpaRepository<LeaveRequest, Long> {

    List<LeaveRequest> findByEmployeeId(Long employeeId);

    Page<LeaveRequest> findByEmployeeId(
            Long employeeId,
            Pageable pageable
    );

    @Query("""
            SELECT COUNT(l) > 0
            FROM LeaveRequest l
            WHERE l.employee.id = :employeeId
            AND l.status = :status
            AND l.startDate <= :endDate
            AND l.endDate >= :startDate
            """)
    boolean existsOverlappingLeave(
            Long employeeId,
            LeaveStatus status,
            LocalDate startDate,
            LocalDate endDate
    );

}