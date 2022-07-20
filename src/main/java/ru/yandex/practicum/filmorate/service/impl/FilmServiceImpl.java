package ru.yandex.practicum.filmorate.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ErrorIdException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.models.Film;
import ru.yandex.practicum.filmorate.models.User;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FilmServiceImpl implements FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    @Override
    public Film get(int filmId) {
        Film film = filmStorage.get(filmId);
        if (film == null) {
            throw new NotFoundException("Film с id = " + filmId + " не найден.");
        }
        return film;
    }

    @Override
    public Film save(Film film) {
        return filmStorage.save(film);
    }

    @Override
    public ArrayList<Film> getFilms() {
        return new ArrayList<Film>(filmStorage.getFilms().values());
    }

    @Override
    public Film update(Film film) {
        if (film.getId() <= 0 || film.getId() > filmStorage.getFilms().keySet().stream().max(Integer::compare).get()){
            throw new ErrorIdException("Введен неккоретный Film  id = " + film.getId());
        }

        return filmStorage.update(film);
    }

    @Override
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

    @Override
    public void deleteLike(int id, int userId) {
        Film film = filmStorage.get(id);
        User user = userStorage.get(userId);
        if (film == null) {
            throw new NotFoundException("Film с id = " + id + " не найден.");
        } else if (user == null) {
            throw new NotFoundException("User с id = " + userId + " не найден.");
        }
        filmStorage.deleteLike(film, user);
    }

    @Override
    public List<Film> getMostLikesMovies(int count) {
        return filmStorage.getMostLikeMovies(count);

    }
}

