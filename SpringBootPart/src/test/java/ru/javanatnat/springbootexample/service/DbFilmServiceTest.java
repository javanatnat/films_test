package ru.javanatnat.springbootexample.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.javanatnat.springbootexample.model.*;
import ru.javanatnat.springbootexample.repository.*;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Testcontainers
@SpringBootTest
public class DbFilmServiceTest {
    @Container
    private static final PostgreSQLContainer<ListsPostgreSQLContainer> postgresqlContainer
            = ListsPostgreSQLContainer.getInstance();

    @Autowired
    DbFilmService dbFilmService;
    @Autowired
    FilmRepository filmRepository;
    @Autowired
    CountryRepository countryRepository;
    @Autowired
    GenreRepository genreRepository;
    @Autowired
    FilmGenreRepository filmGenreRepository;

    @Test
    void testGetAll() {
        FilmDTO filmDTO = new FilmDTO(
                "Film",
                "Country",
                1997,
                "Action, Drama, Fantastic",
                32_000_000);

        FilmDTO saved = dbFilmService.save(filmDTO);

        FilmDTO filmDTOTwo = new FilmDTO(
                "FilmTwo",
                "Country",
                1997,
                "Action, Drama, Fantastic",
                32_000_000);

        FilmDTO savedTwo = dbFilmService.save(filmDTOTwo);

        List<FilmDTO> findFilmDTOs = dbFilmService.getAll();
        assertThat(findFilmDTOs).contains(saved, savedTwo);
    }

    @Test
    void testGetById() {
        FilmDTO filmDTO = new FilmDTO(
                "Film78",
                "Country78",
                1997,
                "Action78, Drama78, Fantastic78",
                32_000_000);

        FilmDTO saved = dbFilmService.save(filmDTO);

        Optional<Country> findCountry = countryRepository.findByNameIgnoreCase("Country78");
        assertThat(findCountry).isPresent();

        Country country = findCountry.get();
        assertThat(country.getName()).isEqualTo("Country78");

        Optional<Film> findFilm = filmRepository.findByNameAndYearAndCountryId(
                "Film78",
                1997,
                country.getId());
        assertThat(findFilm).isPresent();

        Film film = findFilm.get();
        assertThat(film.getName()).isEqualTo("Film78");
        assertThat(film.getBudget()).isEqualTo(32_000_000);
        assertThat(film.getCountryId()).isEqualTo(country.getId());
        assertThat(film.getYear()).isEqualTo(1997);

        FilmDTO findFilmDTO = dbFilmService.getById(film.getId());
        assertThat(findFilmDTO).isEqualTo(filmDTO);
    }

    @Test
    void testGetByIdError() {
        assertThatThrownBy(() -> dbFilmService.getById(150L))
                .isInstanceOf(FilmNotFoundException.class);
    }

    @Test
    void testSave() {
        FilmDTO filmDTO = new FilmDTO(
                "Film43",
                "Country43",
                1997,
                "Action43, Drama43, Fantastic43",
                32_000_000);

        FilmDTO saved = dbFilmService.save(filmDTO);
        assertThat(saved).isEqualTo(filmDTO);

        filmDTO.setCountry("Country243");
        saved = dbFilmService.save(filmDTO);
        assertThat(saved).isEqualTo(filmDTO);
        assertThat(saved.getCountry()).isEqualTo("Country243");

        filmDTO.setGenre("Action43");
        saved = dbFilmService.save(filmDTO);
        assertThat(saved).isEqualTo(filmDTO);
        assertThat(saved.getGenre()).isEqualTo("Action43");
    }

    @Test
    void testDeleteById() {
        FilmDTO filmDTO = new FilmDTO(
                "Film56",
                "Country56",
                1997,
                "Action56, Drama56, Fantastic56",
                32_000_000);

        FilmDTO saved = dbFilmService.save(filmDTO);
        assertThat(saved).isEqualTo(filmDTO);

        Optional<Country> findCountry = countryRepository.findByNameIgnoreCase("Country56");
        assertThat(findCountry).isPresent();
        Country country = findCountry.get();

        List<Genre> genres = genreRepository.findAll();
        List<String> genreNames = genres.stream().map(Genre::getName).toList();
        assertThat(genreNames).contains("Action56", "Drama56", "Fantastic56");

        Optional<Film> findFilm = filmRepository.findByNameAndYearAndCountryId("Film56", 1997, country.getId());
        assertThat(findFilm).isPresent();

        Film film = findFilm.get();
        dbFilmService.deleteById(film.getId());

        findFilm = filmRepository.findByNameAndYearAndCountryId("Film56", 1997, country.getId());
        assertThat(findFilm).isEmpty();

        findCountry = countryRepository.findByNameIgnoreCase("Country56");
        assertThat(findCountry).isPresent();

        genres = genreRepository.findAll();
        genreNames = genres.stream().map(Genre::getName).toList();
        assertThat(genreNames).contains("Action56", "Drama56", "Fantastic56");
    }
}
