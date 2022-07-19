package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.models.Film;
import ru.yandex.practicum.filmorate.models.User;

import java.util.*;

@Component
public class InMemoryFilmStorage implements FilmStorage {
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
        film.getUsersId().add(user.getId());
    }

    @Override
    public void deleteLike(Film film, User user) {
        film.getUsersId().remove(user.getId());
    }

    @Override
    public ArrayList<Film> getMostLikeMovies(int count) {
        ArrayList<Film> listFilms = new ArrayList<>(films.values());
        Collections.sort(listFilms, new Comparator<Film>() {
            @Override
            public int compare(Film film1, Film film2) {
                return film1.getUsersId().size() > film2.getUsersId().size() ? -1 : film1.getUsersId().size() < film2.getUsersId().size() ? 1 : 0;
            }
        });
        if (count >= listFilms.size())
            return listFilms;
        System.out.println(listFilms + "!!!!!!!!!!!!!");
        for (Film film : listFilms)
            System.out.println(film.getUsersId() + "!!!!!!!!!1");
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
