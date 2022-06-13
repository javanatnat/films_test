package ru.javanatnat.javafxexample;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class DbFilmServiceImpl implements DbFilmService {
    private final Set<Film> films;

    public DbFilmServiceImpl() {
        films = new HashSet<>();
        loadFilms();
    }

    @Override
    public List<Film> getAll() {
        return films.stream().toList();
    }

    @Override
    public void save(Film film) {
        Objects.requireNonNull(film);
        films.remove(film);
        films.add(film);
    }

    @Override
    public void delete(Film film) {
        Objects.requireNonNull(film);
        films.remove(film);
    }

    @Override
    public boolean exists(Film film) {
        return films.contains(film);
    }

    private void loadFilms() {
        films.add(new Film("Джуманджи", "США", "Фэнтэзи", 65_000_000, 1995));
        films.add(new Film("Титаник", "США,Мексика,Австралия", "Мелодрама", 200_000_000, 1997));
        films.add(new Film("Матрица", "США,Австралия", "Фантастика", 63_000_000, 1999));
        films.add(new Film("Грязные танцы", "США", "Драма", 6_000_000, 1987));
        films.add(new Film("Бегущий человек", "США", "Фантастика", 27_000_000, 1987));
    }
}
