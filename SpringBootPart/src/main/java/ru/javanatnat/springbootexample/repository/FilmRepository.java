package ru.javanatnat.springbootexample.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.javanatnat.springbootexample.model.Film;
import ru.javanatnat.springbootexample.model.FilmDTO;

import java.util.List;
import java.util.Optional;

public interface FilmRepository extends CrudRepository<Film, Long> {
    @Override
    List<Film> findAll();

    Optional<Film> findByNameAndYearAndCountryId(
            @Param("name") String name,
            @Param("year") int year,
            @Param("country_id") long countryId
            );

    String SELECT = "SELECT " +
                        "f.name as name, " +
                        "c.name as country, " +
                        "f.year as year, " +
                        "string_agg(g.name, ', ') as genre, " +
                        "f.budget as budget " +
                    "FROM film as f " +
                        "JOIN country as c " +
                            "ON f.country_id = c.id " +
                        "LEFT JOIN film_genre as fg " +
                            "ON f.id = fg.film_id " +
                        "LEFT JOIN genre as g " +
                            "ON fg.genre_id = g.id ";

    String WHERE = "WHERE f.id = :id ";

    String GROUP_BY = "GROUP BY " +
                        "f.name, " +
                        "c.name, " +
                        "f.year, " +
                        "f.budget";
    @Query(SELECT + WHERE + GROUP_BY)
    Optional<FilmDTO> findDTOById(@Param("id") long id);

    @Query(SELECT + GROUP_BY)
    List<FilmDTO> findDTOAll();
}
