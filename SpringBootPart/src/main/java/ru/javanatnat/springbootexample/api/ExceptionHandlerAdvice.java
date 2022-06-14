package ru.javanatnat.springbootexample.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.javanatnat.springbootexample.service.FilmNotFoundException;

@ControllerAdvice
public class ExceptionHandlerAdvice {
    @ResponseBody
    @ExceptionHandler(FilmNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String filmNotFoundException(FilmNotFoundException ex) {
        return ex.getMessage();
    }
}
