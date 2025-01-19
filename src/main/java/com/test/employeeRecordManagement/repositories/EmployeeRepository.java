package com.test.employeeRecordManagement.repositories;

import com.test.employeeRecordManagement.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EmployeeRepository extends JpaRepository<Employee,String> , JpaSpecificationExecutor<Employee> {
}
