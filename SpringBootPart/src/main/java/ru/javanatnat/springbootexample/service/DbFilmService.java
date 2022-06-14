package ru.javanatnat.springbootexample.service;

import ru.javanatnat.springbootexample.model.FilmDTO;

import java.util.List;

public interface DbFilmService {
    List<FilmDTO> getAll();
    FilmDTO getById(long id);
    FilmDTO save(FilmDTO film);
    void deleteById(long id);
}
