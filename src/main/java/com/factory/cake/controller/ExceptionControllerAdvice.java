package com.factory.cake.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ExceptionControllerAdvice {
	
private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionControllerAdvice.class);
	
	@ExceptionHandler(BindException.class)
    public ModelAndView handleBindExceptions(BindException ex, Model model) {
		final String mname = "BindException";
        LOGGER.error("entering "+mname);
       List<String> errors = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorClass = error.getClass().getSimpleName();
            String errorMessage = error.getDefaultMessage();
            errors.add(errorClass+" on field "+fieldName+" - "+errorMessage);
        });
        LOGGER.error("exiting "+mname);
        return new ModelAndView("error", Map.of(
//				"brand", "Cake Factory",
				"exceptions", errors
				));
    }
	
	@ExceptionHandler(Exception.class)
    public ModelAndView handleException(HttpServletRequest req, Exception ex, Model model) {
		final String mname = "Exception";
        LOGGER.error("entering "+mname+" - "+ex.getMessage());
        ex.printStackTrace();
        return new ModelAndView("error", Map.of(
//				"brand", "Cake Factory",
				"exception", "Request: " + req.getRequestURL() + " raised " + ex.getClass().getName()+" ***** Go home !"
				));
    }

}