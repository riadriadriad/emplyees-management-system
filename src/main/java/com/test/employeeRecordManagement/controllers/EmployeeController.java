package com.test.employeeRecordManagement.controllers;

import com.test.employeeRecordManagement.dtos.EmployeeDto;
import com.test.employeeRecordManagement.dtos.EmployeeReducedDto;
import com.test.employeeRecordManagement.dtos.Response;
import com.test.employeeRecordManagement.entities.Employee;
import com.test.employeeRecordManagement.entities.enums.EmploymentStatus;
import com.test.employeeRecordManagement.exceptions.BusinessException;
import com.test.employeeRecordManagement.services.EmployeeService;
import com.test.employeeRecordManagement.utils.EmployeeSpecifications;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
@RestController
@RequestMapping("/employees")
@CrossOrigin("*")
@EnableMethodSecurity
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    @PreAuthorize("hasAuthority('SCOPE_ADMIN') or hasAuthority('SCOPE_HR')")
    @PostMapping
    public Employee createEmployee(@RequestBody EmployeeDto employeeDto) throws BusinessException {
        return this.employeeService.createEmployee(employeeDto);
    }
    @PreAuthorize("hasAuthority('SCOPE_ADMIN') or hasAuthority('SCOPE_HR')")
    @GetMapping("/all")
    public Page<Employee> getAllEmployees(int page, int size){
        return this.employeeService.getAllEmployees(page, size);
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN') or hasAuthority('SCOPE_HR')")
    @PutMapping
    public Employee updateEmployee(@RequestParam String employeeId,@RequestBody EmployeeDto employeeDto) throws BusinessException{
        return this.employeeService.updateEmployee(employeeId, employeeDto);
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN') or hasAuthority('SCOPE_HR')")
    @DeleteMapping
    public Response deleteEmployee(@RequestParam String employeeId) throws BusinessException{
        return this.employeeService.deleteEmployee(employeeId);
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN') or hasAuthority('SCOPE_HR')")
    @GetMapping("/search")
    public Page<Employee> searchEmployee(int page,int size,String kw){
        return this.employeeService.searchEmployee(page, size, kw);
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN') or hasAuthority('SCOPE_HR')")
    @GetMapping("/filter")
    public Page<Employee> filterEmployees(@RequestParam(required = false) EmploymentStatus employmentStatus,
                                          @RequestParam(required = false) String department,
                                          @RequestParam(required = false) LocalDate hireDateFrom,
                                          @RequestParam(required = false) LocalDate hireDateTo,
                                          int page, int size){
        return this.employeeService.filterEmployees(employmentStatus, department, hireDateFrom,hireDateTo, page, size);
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN') or hasAuthority('SCOPE_HR')")
    @GetMapping("/employee/{id}")
    public Employee getEmployeeById(@PathVariable String id) throws BusinessException{
        return this.employeeService.getEmployeeById(id);
    }
    @PreAuthorize("hasAuthority('SCOPE_'+#department)")
    @GetMapping("/department/all/{department}")
    public Page<EmployeeReducedDto> getAllEmployeesOfDepartment(@PathVariable String department, int page, int size){
        return this.employeeService.getAllEmployeesOfDepartment(department,page,size);
    }

    @PreAuthorize("hasAuthority('SCOPE_'+#department)")
    @PatchMapping("/department/employee/{id}")
    public EmployeeReducedDto updateEmployee(@PathVariable String id,String department,@RequestBody EmployeeReducedDto employeeReducedDto) throws BusinessException{
        return this.employeeService.updateEmployee(id, department, employeeReducedDto);
    }

    @PreAuthorize("hasAuthority('SCOPE_'+#department)")
    @GetMapping("/department/{department}/search")
    public Page<EmployeeReducedDto> searchEmployee(@PathVariable String department, int page, int size, String kw) {
        return this.employeeService.searchEmployee(department, page, size, kw);
    }
}
