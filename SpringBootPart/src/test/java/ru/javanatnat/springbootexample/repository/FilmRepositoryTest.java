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
import ru.javanatnat.springbootexample.model.FilmDTO;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@DataJdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class FilmRepositoryTest {
    @Container
    private static final PostgreSQLContainer<ListsPostgreSQLContainer> postgresqlContainer
            = ListsPostgreSQLContainer.getInstance();

    @Autowired
    FilmRepository filmRepository;

    @Autowired
    CountryRepository countryRepository;

    @Test
    void testfind() {
        assertThat(filmRepository.findAll().size()).isEqualTo(0);
        assertThat(filmRepository.findDTOAll().size()).isEqualTo(0);

        Country country = countryRepository.save(new Country("Country89"));
        Film film = new Film("Film89", country.getId(), 1990, 23_000_000);
        Film saved = filmRepository.save(film);

        FilmDTO filmDTO = new FilmDTO(
                saved.getName(),
                country.getName(),
                saved.getYear(),
                null,
                saved.getBudget()
                );

        List<FilmDTO> filmDTOs = filmRepository.findDTOAll();
        assertThat(filmDTOs).contains(filmDTO);

        Optional<FilmDTO> findFilmDTO = filmRepository.findDTOById(saved.getId());
        assertThat(findFilmDTO).isPresent();
        assertThat(findFilmDTO.get()).isEqualTo(filmDTO);
    }

    @Test
    void testfind2() {
        Country country = countryRepository.save(new Country("Country67"));
        Film saved1 = filmRepository.save(new Film("Film67", country.getId(), 1990, 23_000_000));
        Film saved2 = filmRepository.save(new Film("Film267", country.getId(), 1990, 23_000_000));

        FilmDTO filmDTO1 = new FilmDTO(
                saved1.getName(),
                country.getName(),
                saved1.getYear(),
                null,
                saved1.getBudget()
        );

        FilmDTO filmDTO2 = new FilmDTO(
                saved2.getName(),
                country.getName(),
                saved2.getYear(),
                null,
                saved2.getBudget()
        );

        List<FilmDTO> filmDTOs = filmRepository.findDTOAll();
        assertThat(filmDTOs).contains(filmDTO1, filmDTO2);

        Optional<FilmDTO> findFilmDTO1 = filmRepository.findDTOById(saved1.getId());
        assertThat(findFilmDTO1).isPresent();
        assertThat(findFilmDTO1.get()).isEqualTo(filmDTO1);

        Optional<FilmDTO> findFilmDTO2 = filmRepository.findDTOById(saved2.getId());
        assertThat(findFilmDTO2).isPresent();
        assertThat(findFilmDTO2.get()).isEqualTo(filmDTO2);
    }

    @Test
    void testfindByNameAndYearAndCountryId() {
        Country country = countryRepository.save(new Country("Country34"));
        Film saved = filmRepository.save(new Film("Film34", country.getId(), 1990, 23_000_000));

        Optional<Film> findFilm = filmRepository.findByNameAndYearAndCountryId(
                saved.getName(),
                saved.getYear(),
                country.getId());
        assertThat(findFilm).isPresent();
        assertThat(findFilm.get()).isEqualTo(saved);
    }
}
