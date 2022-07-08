package ru.yandex.practicum.filmorate.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.models.User;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {
    private final Map<Integer, User> users = new HashMap<>();
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    Integer counter = 0;

    @PostMapping("/users")
    public User addUser(@RequestBody User user) throws ValidationException {
        validate(user);
        user.setId(counter);
        users.put(user.getId(), user);
        counter++;
        log.info("добавили в HashSet Users экземпляром: "+ user.toString());
        return user;
    }

    @GetMapping("/users")
    public Map<Integer, User> getUsers() {
        return users;
    }

    @PutMapping("/users")
    public User updateUser(@RequestBody User user) throws  ValidationException {
        validate(user);
        if (user.getId()==null || user.getId()>counter ||user.getId()<0){
            throw new ValidationException("У фильма, который вы хотите обновить, проблемы с id");
        }
        users.put(user.getId(),user);
        log.info("Обновили в HashSet Users экземпляром: "+ user.toString());
        return user;
    }

    void validate(User user) throws ValidationException {
        if (user.getEmail() == null || user.getEmail().isEmpty() || !user.getEmail().contains("@")) {
            throw new ValidationException("У пользователя, которого вы хотите добавить," +
                    " отсутствует почта или она не содержит @.");
        }
        if (user.getLogin() == null || user.getLogin().isEmpty() || user.getLogin().contains(" ")) {
            throw new ValidationException("У пользователя, которого вы хотите добавить," +
                    " отсутствует логин или он содержит пробелы.");

        }
        if (user.getName() == null ||  user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
        if (user.getBirthday().isAfter(LocalDateTime.now())){
            throw new ValidationException("У пользователя, которого вы хотите добавить,"+
                    " дата рождения в будущем времени.");
        }
    }

}
