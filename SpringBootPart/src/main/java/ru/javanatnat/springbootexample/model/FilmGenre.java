package ru.javanatnat.springbootexample.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Objects;

@Table("film_genre")
public class FilmGenre {
    @Id
    private final Long id;
    private final Long genreId;
    private final Long filmId;

    @PersistenceConstructor
    public FilmGenre(Long id, Long genreId, Long filmId) {
        this.id = id;
        this.genreId = genreId;
        this.filmId = filmId;
    }

    public FilmGenre(Long genreId, Long filmId) {
        this(null, genreId, filmId);
    }

    public Long getId() {
        return id;
    }

    public Long getGenreId() {
        return genreId;
    }

    public Long getFilmId() {
        return filmId;
    }

    @Override
    public String toString() {
        return "FilmGenre{" +
                "id=" + id +
                ", genreId=" + genreId +
                ", filmId=" + filmId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof FilmGenre filmGenre)) {
            return false;
        }

        if (!Objects.equals(id,filmGenre.id)) {
            return false;
        }

        if (!genreId.equals(filmGenre.genreId)) {
            return false;
        }

        return filmId.equals(filmGenre.filmId);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + genreId.hashCode();
        result = 31 * result + filmId.hashCode();
        return result;
    }
}
