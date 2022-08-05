package ru.yandex.practicum.filmorate.storage.db;

import ru.yandex.practicum.filmorate.models.Film;
import ru.yandex.practicum.filmorate.models.User;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.HashMap;
import java.util.List;

public class FilmDbStorage implements FilmStorage {
    @Override
    public Film update(Film film) {
        return null;
    }

    @Override
    public Film get(int FilmId) {
        return null;
    }

    @Override
    public HashMap<Integer, Film> getFilms() {
        return null;
    }

    @Override
    public Film save(Film film) {
        return null;
    }

    @Override
    public void addLike(Film film, User user) {

    }

    @Override
    public void deleteLike(Film film, User user) {

    }

    @Override
    public List<Film> getMostLikeMovies(int count) {
        return null;
    }
}
