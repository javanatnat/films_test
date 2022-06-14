package ru.javanatnat.springbootexample.model;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class FilmDTOTest {
    @Test
    void testGetGenreList() {
        FilmDTO filmDTO = new FilmDTO(
                "filmName",
                "countryName",
                1999,
                "genre1, genre2,  genre3, ",
                45_000_000);

        Set<String> genres = filmDTO.getGenreList();
        assertThat(genres).containsExactlyInAnyOrder("genre1", "genre2", "genre3");

        filmDTO.setGenre("  ,   ,, ,");
        genres = filmDTO.getGenreList();
        assertThat(genres).isEmpty();
    }
}
