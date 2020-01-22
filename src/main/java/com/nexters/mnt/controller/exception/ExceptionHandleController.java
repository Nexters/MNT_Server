package com.nexters.mnt.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExceptionHandleController {

    @ResponseStatus(value = HttpStatus.ALREADY_REPORTED, reason = "Data Duplicated")
    @ExceptionHandler(DataDuplicateException.class)
    public void dataDuplicate(){

    }

}
