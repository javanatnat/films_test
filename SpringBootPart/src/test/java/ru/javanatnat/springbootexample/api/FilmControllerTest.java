package ru.javanatnat.springbootexample.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.javanatnat.springbootexample.model.FilmDTO;
import ru.javanatnat.springbootexample.service.DbFilmService;
import ru.javanatnat.springbootexample.service.FilmNotFoundException;

import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FilmController.class)
public class FilmControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private DbFilmService service;

    private final ObjectMapper mapper = new JsonMapper();

    @Test
    void testGetAll() throws Exception {
        given(service.getAll()).willReturn(List.of(
                new FilmDTO("Film12", "Country12", 1990, "Genre12", 12_000_000)
                ));
        mvc.perform(get("/api/films/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("Film12")))
                .andExpect(jsonPath("$[0].country", is("Country12")))
                .andExpect(jsonPath("$[0].year", is(1990)))
                .andExpect(jsonPath("$[0].genre", is("Genre12")))
                .andExpect(jsonPath("$[0].budget", is(12000000)));
    }

    @Test
    void testGetFilm() throws Exception {
        given(service.getById(120)).willReturn(
                new FilmDTO("Film102", "Country102", 1990, "Genre102", 12_000_000)
        );
        mvc.perform(get("/api/films/120")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value("Film102"))
                .andExpect(jsonPath("country").value("Country102"))
                .andExpect(jsonPath("year").value(1990))
                .andExpect(jsonPath("genre").value("Genre102"))
                .andExpect(jsonPath("budget").value("12000000"));
    }

    @Test
    void testGetFilmError() throws Exception {
        willThrow(new FilmNotFoundException(10)).given(service).getById(10);
        mvc.perform(get("/api/films/10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testSaveFilm() throws Exception {
        FilmDTO filmDTO = new FilmDTO(
                "Film112",
                "Country112",
                1990,
                "Genre112",
                12_000_000);

        given(service.save(filmDTO)).willReturn(filmDTO);

        var params = Map.of(
                "name", "Film112",
                "country", "Country112",
                "year", 1990,
                "genre", "Genre112",
                "budget", 12_000_000);

        mvc.perform(post("/api/films/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(params)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value("Film112"))
                .andExpect(jsonPath("country").value("Country112"))
                .andExpect(jsonPath("year").value(1990))
                .andExpect(jsonPath("genre").value("Genre112"))
                .andExpect(jsonPath("budget").value("12000000"));
    }

    @Test
    void testDeleteFilm() throws Exception {
        willDoNothing().given(service).deleteById(123);
        mvc.perform(delete("/api/films/123")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
