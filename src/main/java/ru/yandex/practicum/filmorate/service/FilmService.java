package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ErrorIdException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.models.Film;
import ru.yandex.practicum.filmorate.models.User;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

@Service
public class FilmService {
    @Autowired
    FilmStorage filmStorage;
    @Autowired
    UserStorage userStorage;
    public Film get(int filmId) {
        Film film = filmStorage.get(filmId);
        if (film == null) {
            throw new NotFoundException("Film с id = " + filmId + " не найден.");
        }
        return film;
    }

    public Film save(Film film) {
        return filmStorage.save(film);
    }

    public ArrayList<Film> getFilms() {
        return new ArrayList<Film>(filmStorage.getFilms().values());
    }

    public Film update(Film film) {
        if (film.getId()<=0|| film.getId()>filmStorage.getFilms().keySet().stream().max( new Comparator<Integer>() {

            @Override
            public int compare(Integer n1, Integer n2) {
                return n1.compareTo(n2);
            }
        }).get())

        {
            throw new ErrorIdException("Введен неккоретный Film  id = " + film.getId());
        }

        return filmStorage.update(film);
    }

    public void addLike(int id, int userId) {
        Film film = filmStorage.get(id);
        User user = userStorage.get(userId);
        if (film == null) {
            throw new NotFoundException("Film с id = " + id + " не найден.");
        } else if (user == null) {
            throw new NotFoundException("User с id = " + userId + " не найден.");
        }
        filmStorage.addLike(film, user);
    }

    public void deleteLike(int id, int userId) {
        Film film = filmStorage.get(id);
        User user = userStorage.get(userId);
        if (film == null) {
            throw new NotFoundException("Film с id = " + id + " не найден.");
        } else if (user == null) {
            throw new NotFoundException("User с id = " + userId + " не найден.");
        }
        filmStorage.deleteLike(film,user);
    }

    public ArrayList<Film> getMostLikesMovies(int count) {
       return filmStorage.getMostLikeMovies(count);

    }
}

