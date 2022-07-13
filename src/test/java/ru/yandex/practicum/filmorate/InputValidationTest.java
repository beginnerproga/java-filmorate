package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controllers.FilmController;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.models.Film;
import ru.yandex.practicum.filmorate.models.User;
import ru.yandex.practicum.filmorate.validation.*;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class InputValidationTest {
    @Test
    void validateNameInFilmController() {
        final Validator validator = new Validator();
        final Film film = new Film();
        film.setReleaseDate(LocalDate.of(2000, 12, 12));
        film.setName("");
        film.setDescription("fefer");
        film.setDuration(244);
        assertThrows(ValidationException.class, () -> validator.validateInFilmController(film));
        film.setName(null);
        assertThrows(ValidationException.class, () -> validator.validateInFilmController(film));

    }

    @Test
    void validateReleaseDateInFilmController() {
        final Validator validator = new Validator();
        final Film film = new Film();
        film.setReleaseDate(LocalDate.of(1000, 12, 12));
        film.setName("vrjrjr");
        film.setDescription("fefer");
        film.setDuration(244);
        assertThrows(ValidationException.class, () -> validator.validateInFilmController(film));

    }

    @Test
    void validateDescriptionInFilmController() {
        final Validator validator = new Validator();
        final Film film = new Film();
        film.setReleaseDate(LocalDate.of(2000, 12, 12));
        film.setName("vrjrjr");
        film.setDescription("a".repeat(201));
        film.setDuration(244);
        assertThrows(ValidationException.class, () -> validator.validateInFilmController(film));

    }
    @Test
    void validateDurationInFilmController() {
        final Validator validator = new Validator();
        final Film film = new Film();
        film.setReleaseDate(LocalDate.of(2000, 12, 12));
        film.setName("vrjrjr");
        film.setDescription("aerfer");
        film.setDuration(-244);
        assertThrows(ValidationException.class, () -> validator.validateInFilmController(film));

    }
    @Test
    void validateBirthdaInUserController() {
        final Validator validator = new Validator();
        final User user = new User();
        user.setBirthday(LocalDate.MAX);
        user.setEmail("qwer@mail.ru");
        user.setName("fwefew");
        user.setLogin("rferfe");
        assertThrows(ValidationException.class, () -> validator.validateInUserController(user));
    }

    @Test
    void validateNameInUserController() {
        final Validator validator = new Validator();
        final User user = new User();
        user.setBirthday(LocalDate.of(2012, 12, 12));
        user.setName("");
        user.setEmail("fwef@mail.ru");
        user.setLogin("fwef");
        validator.validateInUserController(user);
        assertEquals(user.getLogin(), user.getName());
        user.setName(null);
        validator.validateInUserController(user);
        assertEquals(user.getLogin(), user.getName());

    }

    @Test
    void validateEmailInUserController() {
        final Validator validator = new Validator();
        final User user = new User();
        user.setBirthday(LocalDate.of(2012, 12, 12));
        user.setName("reg");
        user.setEmail("fwefmail.ru");
        user.setLogin("erferferf");
        assertThrows(ValidationException.class, () -> validator.validateInUserController(user));
        user.setEmail(null);
        assertThrows(ValidationException.class, () -> validator.validateInUserController(user));


    }

    @Test
    void validateLoginInUserController() {
        final Validator validator = new Validator();
        final User user = new User();
        user.setBirthday(LocalDate.of(2012, 12, 12));
        user.setName("reg");
        user.setEmail("fwef@mail.ru");
        user.setLogin("");
        assertThrows(ValidationException.class, () -> validator.validateInUserController(user));
        user.setLogin("erew  werewe");
        assertThrows(ValidationException.class, () -> validator.validateInUserController(user));


    }
}