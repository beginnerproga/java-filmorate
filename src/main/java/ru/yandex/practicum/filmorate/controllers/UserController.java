package ru.yandex.practicum.filmorate.controllers;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.models.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.validation.Validator;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final Validator validator;

    private final Logger log = LoggerFactory.getLogger(UserController.class);

    @PostMapping()
    public User addUser(@Valid @RequestBody User user) throws ValidationException {
        validator.validateInUserController(user);
        for (User x : userService.getUsers())
            if (x.equals(user)) {
                throw new ValidationException("Такой пользователь уже добавлен.");
            }
        log.info("добавили в HashSet Users экземпляром: " + user);
        return userService.save(user);
    }

    @GetMapping()
    public ArrayList<User> getUsers() {
        return userService.getUsers();
    }

    @PutMapping()
    public User updateUser(@Valid @RequestBody User user) throws ValidationException {
        validator.validateInUserController(user);
        log.info("Обновили в HashSet Users экземпляром: " + user);
        return userService.update(user);
    }

    @GetMapping("/{userId}")
    public User getFilm(@PathVariable int userId) {
        return userService.get(userId);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public void addFriend(@PathVariable int id, @PathVariable int friendId) {
        userService.addFriend(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable int id, @PathVariable int friendId) {
        userService.deleteFriend(id, friendId);

    }

    @GetMapping("/{id}/friends")
    public List<User> getUserFriends(@PathVariable int id) {
        return userService.getUserFriends(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> getMutualFriends(@PathVariable int id, @PathVariable int otherId) {
        return userService.getMutualFriends(id, otherId);
    }
}
