package com.test.employeeRecordManagement.dtos;

public record EmployeeReducedDto( String employeeId, String fullName, String jobTitle, String department) {
    public EmployeeReducedDto() {
        this(null, null, null,null);
    }
}
