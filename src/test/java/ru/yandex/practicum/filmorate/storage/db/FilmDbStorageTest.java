package ru.yandex.practicum.filmorate.storage.db;


import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.models.Film;
import ru.yandex.practicum.filmorate.models.Genre;
import ru.yandex.practicum.filmorate.models.Mpa;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class FilmDbStorageTest {
    private final FilmDbStorage filmStorage;
    private final GenreDbStorage genreStorage;
    @Test
    void testGet() {
        Film film = filmStorage.get(1);
        assertEquals("Джедай", film.getName());
        assertEquals("кррутой джедай", film.getDescription());
        assertEquals(LocalDate.of(2000,12,12), film.getReleaseDate());
        assertEquals(123,film.getDuration());
        assertEquals(new Mpa(1,"G"),film.getMpa());
        assertEquals(3,film.getRate());
        Optional<Film> userOptional = Optional.of(film);
        assertThat(userOptional)
                .isPresent()
                .hasValueSatisfying(x ->
                        assertThat(x).hasFieldOrPropertyWithValue("id", 1)
                );
    }
    @Test
    void testSave() {
        Film film = new Film("feefwfewfwfwfwf","ew",LocalDate.of(2003,11,11),23, Set.of(new Genre(1), new Genre(2)),new Mpa(2),4);
        assertThat(filmStorage.save(film)).hasFieldOrPropertyWithValue("id", 4);
        genreStorage.setGenres(film);
        Film filmCheck = filmStorage.get(4);
        genreStorage.loadFilmGenre(filmCheck);
        assertEquals("feefwfewfwfwfwf", filmCheck.getName());
        assertEquals("ew",filmCheck.getDescription());
        assertEquals(LocalDate.of(2003,11,11),filmCheck.getReleaseDate());
        assertEquals(Set.of(new Genre(1, "Комедия"),new Genre(2,"Драма")),filmCheck.getGenres());
        assertEquals(new Mpa(2, "PG"), filmCheck.getMpa());
        assertEquals(4, filmCheck.getRate());

    }
    @Test
    void testUpdate() {
        Film film = new Film(2,"dwe","dwqdqwdwq",LocalDate.of(2001,11,11),23, Set.of(new Genre(1), new Genre(2)),new Mpa(2),7);
        assertThat(filmStorage.update(film)).hasFieldOrPropertyWithValue("id", 2);
        genreStorage.deleteGenres(film);
        genreStorage.setGenres(film);
        Film filmCheck = filmStorage.get(2);
        genreStorage.loadFilmGenre(filmCheck);
        assertEquals("dwe", filmCheck.getName());
        assertEquals("dwqdqwdwq",filmCheck.getDescription());
        assertEquals(LocalDate.of(2001,11,11),filmCheck.getReleaseDate());
        assertEquals(Set.of(new Genre(1, "Комедия"),new Genre(2,"Драма")),filmCheck.getGenres());
        assertEquals(new Mpa(2, "PG"), filmCheck.getMpa());
        assertEquals(7, filmCheck.getRate());


    }

    @Test
    void testGetFilms() {
        HashMap<Integer, Film> films = filmStorage.getFilms();
        assertEquals(3, films.size());

    }

    @Test
    void testDelete() {
        assertTrue(filmStorage.delete(3));
        Film film = filmStorage.get(3);
        assertNull(film);
    }

}