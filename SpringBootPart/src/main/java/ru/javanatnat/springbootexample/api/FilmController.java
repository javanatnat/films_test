package ru.javanatnat.springbootexample.api;

import org.springframework.web.bind.annotation.*;
import ru.javanatnat.springbootexample.model.FilmDTO;
import ru.javanatnat.springbootexample.service.DbFilmService;

import java.util.List;

@RestController
@RequestMapping("/api/films")
public class FilmController {
    private final DbFilmService dbFilmService;

    public FilmController(DbFilmService dbFilmService) {
        this.dbFilmService = dbFilmService;
    }

    // curl -X 'GET' http://localhost:8080/api/films/
    @GetMapping("/")
    List<FilmDTO> getAll() {
        return dbFilmService.getAll();
    }

    // curl -X 'GET' http://localhost:8080/api/films/1
    @GetMapping("/{id}")
    FilmDTO getFilm(@PathVariable(name = "id") long id) {
        return dbFilmService.getById(id);
    }

    /* curl -X 'POST' -H 'Content-Type: application/json'
     --data '{"name": "Film Name", "year": 1999, "budget": 23000000, "country": "USA", "genre": "Action"}'
     http://localhost:8080/api/films/
    */
    @PostMapping("/")
    FilmDTO saveFilm(@RequestBody FilmDTO filmDTO) {
        return dbFilmService.save(filmDTO);
    }

    // curl -X 'DELETE' http://localhost:8080/api/films/1
    @DeleteMapping("/{id}")
    void deleteFilm(@PathVariable Long id) {
        dbFilmService.deleteById(id);
    }
}
