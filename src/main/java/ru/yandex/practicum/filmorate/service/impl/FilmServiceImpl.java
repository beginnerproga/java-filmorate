package ru.yandex.practicum.filmorate.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ErrorIdException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.models.Film;
import ru.yandex.practicum.filmorate.models.Genre;
import ru.yandex.practicum.filmorate.models.Mpa;
import ru.yandex.practicum.filmorate.models.User;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.GenreStorage;
import ru.yandex.practicum.filmorate.storage.MpaStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class FilmServiceImpl implements FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;
    private final GenreStorage genreStorage;
    private final MpaStorage mpaStorage;

    @Override
    public Film get(int filmId) {
        Film film = filmStorage.get(filmId);
        if (film == null) {
            throw new NotFoundException("Film с id = " + filmId + " не найден.");
        }
        genreStorage.loadFilmGenre(film);
        return film;
    }

    @Override
    public Film save(Film film) {
        Film saveFilm = filmStorage.save(film);
        genreStorage.setGenres(film);
        return saveFilm;

    }

    @Override
    public ArrayList<Film> getFilms() {
        ArrayList<Film> films = new ArrayList<>(filmStorage.getFilms().values());
        for (Film film : films) {
            genreStorage.loadFilmGenre(film);
        }
        return films;
    }

    @Override
    public Film update(Film film) {
        if (film.getId() <= 0 || film.getId() > filmStorage.getFilms().keySet().stream().max(Integer::compare).get()) {
            throw new ErrorIdException("Введен неккоретный Film  id = " + film.getId());
        }
        Film updateFilm = filmStorage.update(film);
        genreStorage.deleteGenres(film);
        genreStorage.setGenres(film);
        return updateFilm;

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
        List<Film> films = filmStorage.getMostLikeMovies(count);
        for (Film film : films) {
            genreStorage.loadFilmGenre(film);
        }
        return films;

    }

    @Override
    public Set<Genre> getGenres() {
        return genreStorage.getGenres();
    }

    @Override
    public Genre getGenresById(int id) {
        if (id <= 0)
            throw new ErrorIdException("Передан неккоретный id ");
        return genreStorage.getGenresById(id);

    }

    @Override
    public Set<Mpa> getMpa() {
        return mpaStorage.getMpa();
    }

    @Override
    public Mpa getMpaById(Integer id) {
        if (id <= 0)
            throw new ErrorIdException("Передан неккоретный id ");
        return mpaStorage.getMpaById(id);

    }
}

