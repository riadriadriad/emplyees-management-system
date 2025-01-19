package com.test.employeeRecordManagement.exceptions;

import org.springframework.http.HttpStatus;

public class BusinessException extends Exception{
    private HttpStatus httpStatus;
    public HttpStatus getHttpStatus(){
        return this.httpStatus;
    }
    public BusinessException(String message,HttpStatus httpStatus){
        super(message);
        this.httpStatus=httpStatus;
    }
}
