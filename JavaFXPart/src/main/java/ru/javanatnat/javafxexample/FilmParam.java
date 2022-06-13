package ru.javanatnat.javafxexample;

public class FilmParam {
    private Film film;
    private final static FilmParam INSTANCE = new FilmParam();

    private FilmParam() {}

    public static FilmParam getInstance() {
        return INSTANCE;
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public boolean isPresent() {
        return !isEmpty();
    }

    public boolean isEmpty() {
        return film == null;
    }

    @Override
    public String toString() {
        return "FilmParam{" +
                "film=" + film +
                '}';
    }
}
