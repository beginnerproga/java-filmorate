package ru.yandex.practicum.filmorate.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.models.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.validation.Validator;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;

@RestController
@RequestMapping("/films")
public class FilmController {
    @Autowired
    FilmService filmService;
    private static final Logger log = LoggerFactory.getLogger(FilmController.class);
    private final Validator validator;

    @Autowired
    public FilmController(Validator validator) {
        this.validator = validator;
    }

    @PostMapping()
    public Film addFilm(@Valid @RequestBody Film film) throws ValidationException {
        validator.validateInFilmController(film);
        for (Film x : filmService.getFilms()) {
            if (film.equals(x)) {
                throw new ValidationException("Такой фильм уже добавлен.");
            }
        }
        Film saveFilm = filmService.save(film);
        log.info("добавили в HashSet Films экземпляром: " + film);
        return saveFilm;
    }

    @GetMapping()
    public ArrayList<Film> getFilms() {
        return filmService.getFilms();
    }

    @PutMapping()
    public Film updateFilm(@Valid @RequestBody Film film) throws ValidationException {
        validator.validateInFilmController(film);
        Film updateFilm = filmService.update(film);
        log.info("обновили в HashSet Films экземпляром: " + film);
        return updateFilm;
    }

    @GetMapping("/{filmId}")
    public Film getFilm(@PathVariable int filmId) {
        return filmService.get(filmId);
    }

    @PutMapping("/{id}/like/{userId}")
    public void addLike(@PathVariable int id, @PathVariable int userId) {
        filmService.addLike(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void deleteLike(@PathVariable int id, @PathVariable int userId) {
        filmService.deleteLike(id, userId);
    }

    @GetMapping("/popular")
    public ArrayList<Film> getMostLikesMovies(@RequestParam(required = false, defaultValue = "10") int count) {
        if (count <= 0)
            count = 10;
        return filmService.getMostLikesMovies(count);

    }

}
