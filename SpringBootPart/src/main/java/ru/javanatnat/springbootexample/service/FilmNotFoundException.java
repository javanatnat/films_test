package ru.javanatnat.springbootexample.service;

public class FilmNotFoundException extends RuntimeException {
    public FilmNotFoundException(long id) {
        super("Could not find film, id = " + id);
    }
}
