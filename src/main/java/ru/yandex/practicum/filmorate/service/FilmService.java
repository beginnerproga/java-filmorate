package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.models.Film;

import java.util.ArrayList;
import java.util.List;

public interface FilmService {
    Film get(int FilmId);

    Film save(Film film);

    ArrayList<Film> getFilms();

    Film update(Film film);

    void addLike(int id, int userId);

    void deleteLike(int id, int userId);

    List<Film> getMostLikesMovies(int count);
}
