package ru.yandex.practicum.filmorate.controllers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.models.Film;
import javax.validation.Valid;
import ru.yandex.practicum.filmorate.validation.Validator;
import java.util.*;

@RestController

public class FilmController {
    private final List<Film> films = new ArrayList<>();
    private Integer counter = 0;
    private static final Logger log = LoggerFactory.getLogger(FilmController.class);

    private final Validator validator;
    @Autowired
    public FilmController(Validator validator) {
        this.validator = validator;

    }

    @PostMapping("/films")
    public Film addFilm(@Valid @RequestBody Film film) throws ValidationException {
        validator.validateInFilmController(film);
        for (Film x: films){
             if (film.equals(x)) {
                  throw new ValidationException("Такой фильм уже добавлен.");
             }
        }
        counter++;
        film.setId(counter);
        films.add(film.getId()-1,film);
        log.info("добавили в HashSet Films экземпляром: "+ film.toString());
        return film;
    }
    @GetMapping("/films")
    public List<Film> getFilms(){

        return films;
    }
    @PutMapping("/films")
    public Film updateFilm(@Valid @RequestBody Film film) throws ValidationException {
        validator.validateInFilmController(film);
        if (film.getId()==null || film.getId()>counter ||film.getId()<0){
            throw new ValidationException("У фильма, который вы хотите обновить, проблемы с id");
        }
        films.set(film.getId()-1, film);
        log.info("обновили в HashSet Films экземпляром: "+ film.toString());

        return film;
    }

}
