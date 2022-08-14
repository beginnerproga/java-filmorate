package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.models.Film;
import ru.yandex.practicum.filmorate.models.Genre;

import java.util.Set;

public interface GenreStorage {
    Set<Genre> getGenres();
    Genre getGenresById(int id);
    void setGenres(Film film);
    void deleteGenres(Film film);
    void loadFilmGenre(Film film);
}
