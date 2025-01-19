package com.test.employeeRecordManagement.controllers;

import com.test.employeeRecordManagement.security.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
@CrossOrigin("*")
@RestController
public class LoginController {
    private final JwtService jwtService;
    public Logger logger= LoggerFactory.getLogger(this.getClass());
    public LoginController(JwtService jwtService) {
        this.jwtService = jwtService;
    }
    @PostMapping("/login")
    public String getToken(Authentication authentication){
        return this.jwtService.generateToken(authentication);
    }
}
