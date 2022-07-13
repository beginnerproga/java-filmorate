package ru.yandex.practicum.filmorate.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.models.User;

import javax.validation.Valid;
import ru.yandex.practicum.filmorate.validation.Validator;
import java.util.ArrayList;
import java.util.List;

@RestController

public class UserController {

    private final List<User> users = new ArrayList<>();
    private final Logger log = LoggerFactory.getLogger(UserController.class);
    Integer counter = 1;
    private final Validator validator;

    @Autowired
    public UserController(Validator validator) {
        this.validator = validator;

    }

    @PostMapping("/users")
    public User addUser(@Valid @RequestBody User user) throws ValidationException {
        validator.validateInUserController(user);
        for (User x : users)
            if (x.equals(user)) {
                throw new ValidationException("Такой пользователь уже добавлен.");
            }
        user.setId(counter);
        users.add(user.getId() - 1, user);
        counter++;
        log.info("добавили в HashSet Users экземпляром: " + user.toString());
        return user;
    }

    @GetMapping("/users")
    public List<User> getUsers() {
        return users;
    }

    @PutMapping("/users")
    public User updateUser(@Valid @RequestBody User user) throws ValidationException {
        validator.validateInUserController(user);
        if (user.getId() == null || user.getId() > counter || user.getId() < 0) {
            throw new ValidationException("У фильма, который вы хотите обновить, проблемы с id");
        }
        users.set(user.getId() - 1, user);
        log.info("Обновили в HashSet Users экземпляром: " + user.toString());
        return user;
    }

}
