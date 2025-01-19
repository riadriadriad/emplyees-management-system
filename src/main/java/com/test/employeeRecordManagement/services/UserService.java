package com.test.employeeRecordManagement.services;

import com.test.employeeRecordManagement.dtos.Response;
import com.test.employeeRecordManagement.dtos.UserDto;
import com.test.employeeRecordManagement.entities.AppUser;
import com.test.employeeRecordManagement.exceptions.BusinessException;
import org.springframework.data.domain.Page;

public interface UserService {
    AppUser getCurrentUser();
    Page<AppUser> getAllUsers(int page,int size);
    AppUser getUserById(Long id) throws BusinessException;
    AppUser createUser(UserDto userDto);
    AppUser updateUser(Long id,UserDto userDto) throws BusinessException;
    Response deleteUser(Long id) throws BusinessException;
}
