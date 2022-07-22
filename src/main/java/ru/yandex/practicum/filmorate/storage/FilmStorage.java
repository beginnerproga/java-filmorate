package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.models.Film;
import ru.yandex.practicum.filmorate.models.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface FilmStorage {
    public Film update(Film film);

    public Film get(int FilmId);

    public HashMap<Integer, Film> getFilms();

    public Film save(Film film);

    void addLike(Film film, User user);

    void deleteLike(Film film, User user);

    List<Film> getMostLikeMovies(int count);

}
