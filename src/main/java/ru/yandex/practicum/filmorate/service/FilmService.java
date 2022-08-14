package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.models.Film;
import ru.yandex.practicum.filmorate.models.Genre;
import ru.yandex.practicum.filmorate.models.Mpa;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public interface FilmService {
    Film get(int FilmId);
    Film save(Film film);
    ArrayList<Film> getFilms();
    Film update(Film film);
    void addLike(int id, int userId);
    void deleteLike(int id, int userId);
    List<Film> getMostLikesMovies(int count);
    Set<Genre> getGenres();
    Genre getGenresById(int id );
    Mpa getMpaById(Integer id);
    Set<Mpa> getMpa();


}
