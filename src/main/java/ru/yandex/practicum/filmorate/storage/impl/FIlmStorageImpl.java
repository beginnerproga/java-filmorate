package ru.yandex.practicum.filmorate.storage.impl;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.models.Film;
import ru.yandex.practicum.filmorate.models.User;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class FIlmStorageImpl implements FilmStorage {
    private final HashMap<Integer, Film> films = new HashMap<>();
    private Integer counter = 0;

    public Integer getCounter() {
        return counter;
    }

    @Override
    public Film get(int UserId) {
        return films.get(UserId);
    }

    @Override
    public Film save(Film film) {
        film.setId(++counter);
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public void addLike(Film film, User user) {
        films.get(film.getId()).getUsersId().add(user.getId());
    }

    @Override
    public void deleteLike(Film film, User user) {
        film.getUsersId().remove(user.getId());
    }

    @Override
    public List<Film> getMostLikeMovies(int count) {
        List<Film> listFilms =  films.values()
                .stream()
                .sorted(Comparator.comparingInt(film0 -> film0.getUsersId().size()))
                .collect(Collectors.toList());
        Collections.reverse(listFilms);
        if (count >= listFilms.size())
           return listFilms;
        listFilms.subList(count, listFilms.size()).clear();
        return listFilms;
    }

    @Override
    public HashMap<Integer, Film> getFilms() {
        return films;
    }

    @Override
    public Film update(Film film) {
        films.put(film.getId(), film);
        return film;
    }
}
