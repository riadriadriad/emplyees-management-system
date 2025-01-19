package com.test.employeeRecordManagement.services;

import com.test.employeeRecordManagement.dtos.EmployeeDto;
import com.test.employeeRecordManagement.dtos.EmployeeReducedDto;
import com.test.employeeRecordManagement.dtos.Response;
import com.test.employeeRecordManagement.entities.Employee;
import com.test.employeeRecordManagement.entities.enums.EmploymentStatus;
import com.test.employeeRecordManagement.exceptions.BusinessException;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

public interface EmployeeService {
    Employee createEmployee(EmployeeDto employeeDto) throws BusinessException;
    Page<Employee> getAllEmployees(int page,int size);
    Employee updateEmployee(String employeeId,EmployeeDto employeeDto) throws BusinessException;
    Response deleteEmployee(String employeeId) throws BusinessException;
    Page<Employee> searchEmployee(int page,int size,String kw);
    Page<Employee> filterEmployees(EmploymentStatus employmentStatus, String department, LocalDate hireDateFrom,LocalDate hireDateTo,int page,int size);
    Employee getEmployeeById(String id) throws BusinessException;
    Page<EmployeeReducedDto> getAllEmployeesOfDepartment(String department, int page, int size);
    EmployeeReducedDto updateEmployee(String id,String department,EmployeeReducedDto employeeReducedDto) throws BusinessException;
    Page<EmployeeReducedDto> searchEmployee(String department,int page,int size,String kw);


}
