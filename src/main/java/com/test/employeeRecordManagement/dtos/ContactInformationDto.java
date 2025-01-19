package com.test.employeeRecordManagement.dtos;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

public record ContactInformationDto(String phoneNumber, @Email(message = " Invalid email ") String email) {
}
