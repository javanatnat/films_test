package ru.javanatnat.javafxexample;

import java.util.List;

public interface DbFilmService {
    List<Film> getAll();
    void save(Film film);
    void delete(Film film);
    boolean exists(Film film);
}
