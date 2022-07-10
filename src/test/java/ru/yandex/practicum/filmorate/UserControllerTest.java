package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controllers.UserController;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.models.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {
    @Test
    void validateBirthday() {
        final UserController userController = new UserController();
        final User user = new User();
        user.setBirthday(LocalDate.MAX);
        user.setEmail("qwer@mail.ru");
        user.setName("fwefew");
        user.setLogin("rferfe");
        assertThrows(ValidationException.class, () -> userController.validate(user));
    }

    @Test
    void validateName() {
        final UserController userController = new UserController();
        final User user = new User();
        user.setBirthday(LocalDate.of(2012, 12, 12));
        user.setName("");
        user.setEmail("fwef@mail.ru");
        user.setLogin("fwef");
        userController.validate(user);
        assertEquals(user.getLogin(), user.getName());
        user.setName(null);
        userController.validate(user);
        assertEquals(user.getLogin(), user.getName());

    }

    @Test
    void validateEmail() {
        final UserController userController = new UserController();
        final User user = new User();
        user.setBirthday(LocalDate.of(2012, 12, 12));
        user.setName("reg");
        user.setEmail("fwefmail.ru");
        user.setLogin("erferferf");
        assertThrows(ValidationException.class, () -> userController.validate(user));
        user.setEmail(null);
        assertThrows(ValidationException.class, () -> userController.validate(user));


    }

    @Test
    void validateLogin() {
        final UserController userController = new UserController();
        final User user = new User();
        user.setBirthday(LocalDate.of(2012, 12, 12));
        user.setName("reg");
        user.setEmail("fwef@mail.ru");
        user.setLogin("");
        assertThrows(ValidationException.class, () -> userController.validate(user));
        user.setLogin("erew  werewe");
        assertThrows(ValidationException.class, () -> userController.validate(user));


    }
}