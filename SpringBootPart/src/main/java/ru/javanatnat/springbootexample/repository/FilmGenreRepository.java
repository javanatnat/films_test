package ru.javanatnat.springbootexample.repository;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.javanatnat.springbootexample.model.FilmGenre;

import java.util.List;

public interface FilmGenreRepository extends CrudRepository<FilmGenre, Long> {
    @Override
    List<FilmGenre> findAll();

    @Modifying
    @Query("DELETE FROM film_genre as fg WHERE fg.film_id = :film_id")
    void deleteByFilm(@Param("film_id") Long filmId);
}
