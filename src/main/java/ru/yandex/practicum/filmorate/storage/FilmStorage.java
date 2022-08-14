package ru.yandex.practicum.filmorate.storage;
import ru.yandex.practicum.filmorate.models.Film;
import ru.yandex.practicum.filmorate.models.User;
import java.util.HashMap;
import java.util.List;

public interface FilmStorage {
    Film update(Film film);

    Film get(int filmId);

    HashMap<Integer, Film> getFilms();

    Film save(Film film);

    void addLike(Film film, User user);

    void deleteLike(Film film, User user);

    List<Film> getMostLikeMovies(int count);

}
