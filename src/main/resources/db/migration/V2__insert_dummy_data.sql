INSERT INTO employees (
    name,
    role,
    annual_leave_quota
)
VALUES
(
    'Stephanie',
    'MANAGER',
    12
),
(
    'John',
    'EMPLOYEE',
    10
),
(
    'Sarah',
    'EMPLOYEE',
    8
);

INSERT INTO leave_requests (
    employee_id,
    start_date,
    end_date,
    reason,
    status
)
VALUES
(
    2,
    '2026-05-10',
    '2026-05-12',
    'Family vacation',
    'APPROVED'
),
(
    2,
    '2026-06-01',
    '2026-06-02',
    'Medical leave',
    'PENDING'
),
(
    3,
    '2026-07-15',
    '2026-07-18',
    'Personal leave',
    'REJECTED'
);