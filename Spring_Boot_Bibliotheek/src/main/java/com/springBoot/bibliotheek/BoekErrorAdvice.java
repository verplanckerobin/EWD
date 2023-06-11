package com.springBoot.bibliotheek;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import exceptions.AuteurNotFoundException;
import exceptions.BoekNotFoundException;

// Deze annotatie geeft aan dat de klasse advies geeft (global exception handling) voor REST controllers. 
// Het combineert @ControllerAdvice en @ResponseBody, waardoor de klasse uitzonderingen kan afhandelen en direct de response body kan retourneren.
@RestControllerAdvice
public class BoekErrorAdvice {

    // Geeft aan dat de return value van de geannoteerde methoden direct naar de
    // HTTP respons body moet worden geschreven
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
