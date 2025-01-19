package com.test.employeeRecordManagement.utils;

import com.test.employeeRecordManagement.entities.Employee;
import com.test.employeeRecordManagement.entities.enums.EmploymentStatus;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class EmployeeSpecifications {
    public static Specification<Employee> searchByKw(String kw){
        return (root, query, criteriaBuilder) -> {
           return criteriaBuilder.or(
                   criteriaBuilder.like(root.get("fullName"),"%"+kw+"%"),
                   criteriaBuilder.like(root.get("employeeId"),"%"+kw+"%"),
                   criteriaBuilder.like(root.get("jobTitle"),"%"+kw+"%"),
                   criteriaBuilder.like(root.get("department"),"%"+kw+"%")
           );
        };
    }

    public static Specification<Employee> hasEmploymentStatus(EmploymentStatus employmentStatus){
        return (root, query, criteriaBuilder) -> {
            if(employmentStatus==null)
                return criteriaBuilder.conjunction();
          return  criteriaBuilder.equal(root.get("employmentStatus"),employmentStatus);
        };
    }
    public static Specification<Employee> hasDepartment(String department){
        return (root, query, criteriaBuilder) -> {
            if(department==null || department.isBlank())
                return criteriaBuilder.conjunction();
            return  criteriaBuilder.equal(root.get("department"),department);
        };
    }

    public static Specification<Employee> hasHireDate(LocalDate from,LocalDate to){
        return (root, query, criteriaBuilder) -> {
            if(from==null && to==null)
                return criteriaBuilder.conjunction();
            if(from==null) return criteriaBuilder.lessThanOrEqualTo(root.get("hireDate"),to);
            if(to==null) return criteriaBuilder.greaterThan(root.get("hireDate"),from);
            return criteriaBuilder.between(root.get("hireDate"),from,to);
        };
    }
    public static Specification<Employee> hasIdAndDepartment(String id,String department){
        return (root, query, criteriaBuilder) -> {
          return criteriaBuilder.and(criteriaBuilder.equal(root.get("employeeId"),id),
                  criteriaBuilder.equal(root.get("department"),department));
        };
    }

    public static Specification<Employee> hasStrictDepartment(String department){
        return (root, query, criteriaBuilder) -> {
            return  criteriaBuilder.equal(root.get("department"),department);
        };
    }
}
