package ru.yandex.practicum.filmorate.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.models.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.validation.Validator;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/films")
@RequiredArgsConstructor
@Slf4j
public class FilmController {
    private final FilmService filmService;
    private final Validator validator;

    @PostMapping()
    public Film addFilm(@Valid @RequestBody Film film) throws ValidationException {
        validator.validateInFilmController(film);
        for (Film x : filmService.getFilms()) {
            if (film.equals(x)) {
                throw new ValidationException("Такой фильм уже добавлен.");
            }
        }
        log.info("добавили в HashSet Films экземпляром: " + film);
        return filmService.save(film);
    }

    @GetMapping()
    public ArrayList<Film> getFilms() {
        return filmService.getFilms();
    }

    @PutMapping()
    public Film updateFilm(@Valid @RequestBody Film film) throws ValidationException {
        validator.validateInFilmController(film);
        log.info("обновили в HashSet Films экземпляром: " + film);
        return filmService.update(film);

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
    public List<Film> getMostLikesMovies(@RequestParam(required = false, defaultValue = "10") int count) {
        if (count <= 0)
            count = 10;
        return filmService.getMostLikesMovies(count);

    }

}
