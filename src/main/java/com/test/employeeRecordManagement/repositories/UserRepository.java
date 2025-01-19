package com.test.employeeRecordManagement.repositories;

import com.test.employeeRecordManagement.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<AppUser,Long> {
    AppUser findByUsername(String username);
}
