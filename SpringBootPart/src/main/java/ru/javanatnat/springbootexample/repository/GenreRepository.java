package ru.javanatnat.springbootexample.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.javanatnat.springbootexample.model.Genre;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface GenreRepository extends CrudRepository<Genre, Long> {
    @Override
    List<Genre> findAll();

    @Query("SELECT " +
                "g.id, " +
                "g.name " +
            "FROM genre as g " +
                "JOIN film_genre as fg " +
                    "ON g.id = fg.genre_id " +
            "WHERE fg.film_id = :film_id")
    Set<Genre> getGenresByFilmId(@Param("film_id") Long filmId);

    Optional<Genre> findByNameIgnoreCase(@Param("name") String name);
}
