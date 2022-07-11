package ru.yandex.practicum.filmorate.controllers;

import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.models.Film;
import com.google.gson.Gson;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.*;

@RestController
public class FilmController {
    private final List<Film> films = new ArrayList<>();
    private Integer counter = 0;
    private  final Logger log = LoggerFactory.getLogger(FilmController.class);


    @PostMapping("/films")
    public Film addFilm(@Valid @RequestBody Film film) throws ValidationException {
        validate(film);
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
        validate(film);
        if (film.getId()==null || film.getId()>counter ||film.getId()<0){
            throw new ValidationException("У фильма, который вы хотите обновить, проблемы с id");
        }
        films.set(film.getId()-1, film);
        log.info("обновили в HashSet Films экземпляром: "+ film.toString());

        return film;
    }
    public void validate(Film film) throws ValidationException {
        if (film.getName()==null ||film.getName().isEmpty()){
            log.error("У фильма, который вы хотите добавить, отсутствует название.");
            throw new ValidationException("У фильма, который вы хотите добавить, отсутствует название.");
        }
        if (film.getDescription().length()>200){
            log.error("У фильма, который вы хотите добавить, слишком длинное описание.");
            throw  new ValidationException("У фильма, который вы хотите добавить, слишком длинное описание.");
        }
        if (film.getReleaseDate().isBefore(LocalDate.of(1895,12,28))){
            log.error("У фильма, который вы хотите добавить, дата релиза слишком ранняя.");
            throw new ValidationException("У фильма, который вы хотите добавить, дата релиза слишком ранняя.");
        }
        if (film.getDuration()<=0){
            log.error("У фильма, который вы хотите добавить, отрицательная или нулевая продолжительность.");
            throw new ValidationException("У фильма, который вы хотите добавить, отрицательная или нулевая продолжительность.");
        }
    }
}
