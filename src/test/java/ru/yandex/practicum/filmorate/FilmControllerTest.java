package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controllers.FilmController;
import ru.yandex.practicum.filmorate.controllers.UserController;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.models.Film;
import ru.yandex.practicum.filmorate.models.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class FilmControllerTest {
    @Test
    void validateName() {
        final FilmController filmController = new FilmController();
        final Film film = new Film();
        film.setReleaseDate(LocalDate.of(2000, 12, 12));
        film.setName("");
        film.setDescription("fefer");
        film.setDuration(244);
        assertThrows(ValidationException.class, () -> filmController.validate(film));
        film.setName(null);
        assertThrows(ValidationException.class, () -> filmController.validate(film));

    }

    @Test
    void validateReleaseDate() {
        final FilmController filmController = new FilmController();
        final Film film = new Film();
        film.setReleaseDate(LocalDate.of(1000, 12, 12));
        film.setName("vrjrjr");
        film.setDescription("fefer");
        film.setDuration(244);
        assertThrows(ValidationException.class, () -> filmController.validate(film));

    }

    @Test
    void validateDescription() {
        final FilmController filmController = new FilmController();
        final Film film = new Film();
        film.setReleaseDate(LocalDate.of(2000, 12, 12));
        film.setName("vrjrjr");
        film.setDescription("a".repeat(201));
        film.setDuration(244);
        assertThrows(ValidationException.class, () -> filmController.validate(film));

    }
    @Test
    void validateDuration() {
        final FilmController filmController = new FilmController();
        final Film film = new Film();
        film.setReleaseDate(LocalDate.of(2000, 12, 12));
        film.setName("vrjrjr");
        film.setDescription("aerfer");
        film.setDuration(-244);
        assertThrows(ValidationException.class, () -> filmController.validate(film));

    }
}