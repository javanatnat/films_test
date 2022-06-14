package ru.javanatnat.springbootexample.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.javanatnat.springbootexample.model.*;
import ru.javanatnat.springbootexample.repository.CountryRepository;
import ru.javanatnat.springbootexample.repository.FilmGenreRepository;
import ru.javanatnat.springbootexample.repository.FilmRepository;
import ru.javanatnat.springbootexample.repository.GenreRepository;
import ru.javanatnat.springbootexample.sessionmanager.TransactionManager;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DbFilmServiceImpl implements DbFilmService {
    private static final Logger LOG = LoggerFactory.getLogger(DbFilmServiceImpl.class);
    private final FilmRepository filmRepository;
    private final FilmGenreRepository filmGenreRepository;
    private final GenreRepository genreRepository;
    private final CountryRepository countryRepository;
    private final TransactionManager transactionManager;

    public DbFilmServiceImpl(
            FilmRepository filmRepository,
            FilmGenreRepository filmGenreRepository,
            GenreRepository genreRepository,
            CountryRepository countryRepository,
            TransactionManager transactionManager
    ) {
        this.filmRepository = filmRepository;
        this.filmGenreRepository = filmGenreRepository;
        this.genreRepository = genreRepository;
        this.countryRepository = countryRepository;
        this.transactionManager = transactionManager;
    }

    @Override
    public List<FilmDTO> getAll() {
        List<FilmDTO> films = filmRepository.findDTOAll();
        LOG.info("find all films: {}", films);
        return films;
    }

    @Override
    public FilmDTO getById(long id) {
        FilmDTO film = filmRepository.findDTOById(id).orElseThrow(() -> new FilmNotFoundException(id));
        LOG.info("find film: {}", film);
        return film;
    }

    @Override
    public FilmDTO save(FilmDTO film) {
        LOG.info("film for save: {}", film);
        return transactionManager.doInTransaction(() -> {
            String countryName = film.getCountry();

            Optional<Country> findCountry = countryRepository.findByNameIgnoreCase(countryName);
            Country country = findCountry.orElseGet(() -> countryRepository.save(new Country(countryName)));
            LOG.info("country: {}", country);

            Optional<Film> findFilm = filmRepository.findByNameAndYearAndCountryId(
                    film.getName(),
                    film.getYear(),
                    country.getId()
            );

            Film newFilm;
            Set<String> filmGenres;

            if (findFilm.isPresent()) {
                newFilm = findFilm.get();
                LOG.info("findFilm: {}", newFilm);
                newFilm.setBudget(film.getBudget());

                filmGenres = genreRepository.getGenresByFilmId(newFilm.getId())
                        .stream()
                        .map(Genre::getName)
                        .collect(Collectors.toSet());
                LOG.info("film genres: {}", filmGenres);

            } else {
                newFilm = new Film(
                        film.getName(),
                        country.getId(),
                        film.getYear(),
                        film.getBudget());
                LOG.info("new film: {}", newFilm);
                filmGenres = new HashSet<>();
            }

            Film saved = filmRepository.save(newFilm);
            LOG.info("saved film: {}", saved);

            long filmId = saved.getId();

            Set<String> genres = film.getGenreList();
            LOG.info("genres from dto: {}", genres);

            Set<Genre> savedGenres = new HashSet<>();
            if (!genres.equals(filmGenres)) {
                if (!filmGenres.isEmpty()) {
                    filmGenreRepository.deleteByFilm(filmId);
                    LOG.info("film's genres successfully delete");
                }

                for (String genreName : genres) {
                    LOG.info("genre: {}", genreName);
                    Optional<Genre> findGenre = genreRepository.findByNameIgnoreCase(genreName);
                    Genre genre = findGenre.orElseGet(() -> genreRepository.save(new Genre(genreName)));
                    LOG.info("genre: {}", genre);
                    savedGenres.add(genre);

                    filmGenreRepository.save(
                            new FilmGenre(
                                    genre.getId(),
                                    filmId
                            ));
                }
            }

            film.setGenre(
                    savedGenres
                            .stream()
                            .map(Genre::getName)
                            .collect(Collectors.joining(", ")));
            film.setCountry(country.getName());
            return film;
        });
    }

    @Override
    public void deleteById(long id) {
        LOG.info("delete by id: {}", id);
        Film film = filmRepository.findById(id).orElseThrow(() -> new FilmNotFoundException(id));
        LOG.info("find film by id: {}", film);
        transactionManager.doInTransaction(() -> {
            filmGenreRepository.deleteByFilm(id);
            filmRepository.deleteById(id);
            return null;
        });
        LOG.info("film (id = {}) successfully delete", id);
    }
}
