package com.test.employeeRecordManagement.dtos;


import com.test.employeeRecordManagement.entities.enums.EmploymentStatus;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record EmployeeDto( String employeeId,
                           String fullName,
                          String jobTitle,
                           String department,
                           LocalDate hireDate,
                           EmploymentStatus employmentStatus, ContactInformationDto contactInformationDto, String address) {
}
