package com.test.employeeRecordManagement.entities.enums;


public enum EmploymentStatus {
    FULL_TIME("Full-Time"),
    PART_TIME("Part-Time"),
    CONTRACTUAL("Contractual"),
    TEMPORARY("Temporary"),
    PROBATIONARY("Probationary"),
    INTERN("Intern"),
    CASUAL("Casual/On-Call"),
    SEASONAL("Seasonal"),
    RETIRED("Retired"),
    TERMINATED("Terminated"),
    ON_LEAVE("On Leave");

    private final String description;

    EmploymentStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
