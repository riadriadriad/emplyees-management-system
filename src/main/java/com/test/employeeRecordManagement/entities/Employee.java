package com.test.employeeRecordManagement.entities;

import com.test.employeeRecordManagement.entities.enums.EmploymentStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Entity
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Employee {
    @Id
    private String employeeId;
    @NotBlank(message = "The name field is required")
    private String fullName;
    @NotBlank(message = "The job title field is required")
    private String jobTitle;
    @NotBlank(message = "Please enter a department")
    private String department;
    @NotNull(message = "the hire date is required")
    private LocalDate hireDate;
    @Enumerated(EnumType.STRING)
    private EmploymentStatus employmentStatus;
    @OneToOne(cascade = {CascadeType.ALL,CascadeType.MERGE})
    private ContactInformation contactInformation;
    private String address;
}
