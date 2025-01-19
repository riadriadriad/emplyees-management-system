package com.test.employeeRecordManagement.integrationTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.employeeRecordManagement.dtos.ContactInformationDto;
import com.test.employeeRecordManagement.dtos.EmployeeDto;
import com.test.employeeRecordManagement.entities.ContactInformation;
import com.test.employeeRecordManagement.entities.Employee;
import com.test.employeeRecordManagement.entities.enums.EmploymentStatus;
import com.test.employeeRecordManagement.repositories.EmployeeRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import java.time.LocalDate;

@SpringBootTest
@AutoConfigureMockMvc
@EnableAutoConfiguration(exclude = { SecurityAutoConfiguration.class })
@Transactional
@ActiveProfiles("test")
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        employeeRepository.deleteAll();

        Employee employee1 = Employee.builder()
                .employeeId("1")
                .fullName("John Doe")
                .jobTitle("Developer")
                .department("IT")
                .hireDate(LocalDate.of(2020, 1, 1))
                .employmentStatus(EmploymentStatus.FULL_TIME)
                .contactInformation(new ContactInformation(null,"+123456789", "john.doe@example.com"))
                .address("123 Main St")
                .build();

        Employee employee2 = Employee.builder()
                .employeeId("2")
                .fullName("Jane Smith")
                .jobTitle("Tester")
                .department("QA")
                .hireDate(LocalDate.of(2019, 5, 15))
                .employmentStatus(EmploymentStatus.PART_TIME)
                .contactInformation(new ContactInformation(null,"+987654321", "jane.smith@example.com"))
                .address("456 Elm St")
                .build();

        employeeRepository.save(employee1);
        employeeRepository.save(employee2);
    }
    @Test
    @WithMockUser(username="riad",authorities = {"SCOPE_ADMIN"})
    void testFilterEmployeesByDepartment() throws Exception {
        mockMvc.perform(get("/employees/filter")
                        .param("department", "IT")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].department").value("IT"))
                .andExpect(jsonPath("$.content.length()").value(1));
    }
    @WithMockUser(username="riad",authorities = {"SCOPE_ADMIN"})
    @Test
    void testFilterEmployeesByEmploymentStatus() throws Exception {
        mockMvc.perform(get("/employees/filter")
                        .param("employmentStatus", "FULL_TIME")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].employmentStatus").value("FULL_TIME"))
                .andExpect(jsonPath("$.content.length()").value(1));
    }


    @WithMockUser(username="riad",authorities = {"SCOPE_ADMIN"})
    @Test
    void testFilterEmployeesByHireDateRange() throws Exception {
        mockMvc.perform(get("/employees/filter")
                        .param("hireDateFrom", "2019-01-01")
                        .param("hireDateTo", "2019-12-31")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].hireDate").value("2019-05-15"))
                .andExpect(jsonPath("$.content.length()").value(1));
    }

    @WithMockUser(username="riad",authorities = {"SCOPE_ADMIN"})
    @Test
    void testFilterEmployeesWithAllCriteria() throws Exception {
        mockMvc.perform(get("/employees/filter")
                        .param("employmentStatus", "PART_TIME")
                        .param("department", "QA")
                        .param("hireDateFrom", "2018-01-01")
                        .param("hireDateTo", "2020-01-01")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].department").value("QA"))
                .andExpect(jsonPath("$.content[0].employmentStatus").value("PART_TIME"))
                .andExpect(jsonPath("$.content.length()").value(1));
    }

    @WithMockUser(username="riad",authorities = {"SCOPE_ADMIN"})
    @Test
    void testFilterEmployeesWithoutCriteria() throws Exception {
        mockMvc.perform(get("/employees/filter")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(2));
    }

    @WithMockUser(username="riad",authorities = {"SCOPE_ADMIN"})
    @Test
    void testGetAllEmployees() throws Exception {
        mockMvc.perform(get("/employees/all")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(2));
    }

    @WithMockUser(username="riad",authorities = {"SCOPE_ADMIN"})
    @Test
    void testCreateEmployee() throws Exception {
        EmployeeDto employeeDto = new EmployeeDto(
                "3", "Alice Johnson", "Manager", "HR",
                LocalDate.of(2021, 3, 1), EmploymentStatus.FULL_TIME,
                new ContactInformationDto("123456789", "alice.johnson@example.com"), "789 Oak St");

        mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employeeDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value("Alice Johnson"));
    }

    @WithMockUser(username="riad",authorities = {"SCOPE_ADMIN"})
    @Test
    void testUpdateEmployee() throws Exception {
        EmployeeDto employeeDto = new EmployeeDto(
                "1", "John Doe Updated", "Senior Developer", "IT",
                LocalDate.of(2020, 1, 1), EmploymentStatus.FULL_TIME,
                new ContactInformationDto("123456789", "john.updated@example.com"), "123 Main St");

        mockMvc.perform(put("/employees")
                        .param("employeeId", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employeeDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value("John Doe Updated"));
    }

    @WithMockUser(username="riad",authorities = {"SCOPE_ADMIN"})
    @Test
    void testDeleteEmployee() throws Exception {
        mockMvc.perform(delete("/employees")
                        .param("employeeId", "1"))
                .andExpect(status().isOk());

        Assertions.assertFalse(employeeRepository.existsById("1"));
    }
    @WithMockUser(username="riad",authorities = {"SCOPE_MANAGER"})
    @Test
    void testDeleteEmployeeWithManager() throws Exception {
        mockMvc.perform(delete("/employees")
                        .param("employeeId", "1"))
                .andExpect(status().isForbidden());

    }

    @WithMockUser(username="riad",authorities = {"SCOPE_ADMIN"})
    @Test
    void testGetEmployeeById() throws Exception {
        mockMvc.perform(get("/employees/employee/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.employeeId").value("1"));
    }
}
