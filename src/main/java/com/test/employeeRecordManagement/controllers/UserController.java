package com.test.employeeRecordManagement.controllers;

import com.test.employeeRecordManagement.dtos.Response;
import com.test.employeeRecordManagement.dtos.UserDto;
import com.test.employeeRecordManagement.entities.AppUser;
import com.test.employeeRecordManagement.exceptions.BusinessException;
import com.test.employeeRecordManagement.services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@EnableMethodSecurity
@CrossOrigin("*")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @GetMapping("/all")
    public Page<AppUser> getAllUsers(int page,int size){
        return this.userService.getAllUsers(page, size);
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @GetMapping("/user/{id}")
    public AppUser getUserById(@PathVariable Long id) throws BusinessException{
        return this.userService.getUserById(id);
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @PostMapping
    public AppUser createUser(@RequestBody UserDto userDto){
        return this.userService.createUser(userDto);
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @PutMapping("/user/{id}")
    public AppUser updateUser(@PathVariable Long id,@RequestBody UserDto userDto) throws BusinessException{
        return this.userService.updateUser(id, userDto);
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @DeleteMapping
    public Response deleteUser(Long id) throws BusinessException{
        return this.userService.deleteUser(id);
    }
}
