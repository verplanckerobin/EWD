package com.springBoot.bibliotheek;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import exceptions.AuteurNotFoundException;
import exceptions.BoekNotFoundException;

@RestControllerAdvice
public class BoekErrorAdvice {

    @ResponseBody
    @ExceptionHandler(BoekNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String employeeNotFoundHandler(BoekNotFoundException ex) {
	return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(AuteurNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String employeeNotFoundHandler(AuteurNotFoundException ex) {
	return ex.getMessage();
    }

}
