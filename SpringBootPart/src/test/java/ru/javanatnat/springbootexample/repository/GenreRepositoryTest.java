package ru.javanatnat.springbootexample.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.javanatnat.springbootexample.model.Country;
import ru.javanatnat.springbootexample.model.Film;
import ru.javanatnat.springbootexample.model.FilmGenre;
import ru.javanatnat.springbootexample.model.Genre;

import java.util.Locale;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@DataJdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class GenreRepositoryTest {
    @Container
    private static final PostgreSQLContainer<ListsPostgreSQLContainer> postgresqlContainer
            = ListsPostgreSQLContainer.getInstance();

    @Autowired
    GenreRepository genreRepository;
    @Autowired
    FilmGenreRepository filmGenreRepository;
    @Autowired
    FilmRepository filmRepository;
    @Autowired
    CountryRepository countryRepository;

    @Test
    void testDeleteByFilm() {
        Genre genre = genreRepository.save(new Genre("Genre55"));
        Country country = countryRepository.save(new Country("Country55"));
        Film film = filmRepository.save(new Film("Film55", country.getId(), 1990, 21_000_000));
        FilmGenre filmGenre = filmGenreRepository.save(new FilmGenre(genre.getId(), film.getId()));

        Optional<FilmGenre> findFilmGenre = filmGenreRepository.findById(filmGenre.getId());
        assertThat(findFilmGenre).isPresent();
        assertThat(findFilmGenre.get()).isEqualTo(filmGenre);

        filmGenreRepository.deleteByFilm(film.getId());
        findFilmGenre = filmGenreRepository.findById(filmGenre.getId());
        assertThat(findFilmGenre).isEmpty();
    }

    @Test
    void testGetGenresByFilmId() {
        Genre genre = genreRepository.save(new Genre("Genre33"));
        Genre genreTwo = genreRepository.save(new Genre("GenreTwo33"));
        Country country = countryRepository.save(new Country("Country33"));
        Film film = filmRepository.save(new Film("Film33", country.getId(), 1990, 21_000_000));
        FilmGenre filmGenre = filmGenreRepository.save(new FilmGenre(genre.getId(), film.getId()));
        FilmGenre filmGenreTwo = filmGenreRepository.save(new FilmGenre(genreTwo.getId(), film.getId()));

        Set<Genre> genres = genreRepository.getGenresByFilmId(film.getId());
        assertThat(genres).containsExactlyInAnyOrder(genre, genreTwo);
    }

    @Test
    void testFindByNameIgnoreCase() {
        Genre genre = genreRepository.save(new Genre("Genre21"));
        Optional<Genre> findGenre = genreRepository.findByNameIgnoreCase(genre.getName());
        assertThat(findGenre).isPresent();
        assertThat(findGenre.get()).isEqualTo(genre);

        findGenre = genreRepository.findByNameIgnoreCase(genre.getName().toLowerCase(Locale.ROOT));
        assertThat(findGenre).isPresent();
        assertThat(findGenre.get()).isEqualTo(genre);
    }
}
