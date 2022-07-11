package ru.yandex.practicum.filmorate.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.models.User;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    private final List<User> users = new ArrayList<>();
    private  final Logger log = LoggerFactory.getLogger(UserController.class);
    Integer counter = 1;

    @PostMapping("/users")
    public User addUser(@Valid  @RequestBody  User user) throws ValidationException {
        validate(user);
        for (User x: users)
            if (x.equals(user)){
                 throw new ValidationException("Такой пользователь уже добавлен.");
            }
        user.setId(counter);
        users.add(user.getId()-1, user);
        counter++;
        log.info("добавили в HashSet Users экземпляром: "+ user.toString());
    return user;
    }

    @GetMapping("/users")
    public List<User> getUsers() {
        return users;
    }

    @PutMapping("/users")
    public User updateUser(@Valid @RequestBody User user) throws  ValidationException {
        validate(user);
        if (user.getId()==null || user.getId()>counter ||user.getId()<0){
            throw new ValidationException("У фильма, который вы хотите обновить, проблемы с id");
        }
        users.set(user.getId()-1,user);
        log.info("Обновили в HashSet Users экземпляром: "+ user.toString());
        return user;
    }

    public void validate(User user) throws ValidationException {
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
        if (user.getBirthday().isAfter(LocalDate.now())){
            throw new ValidationException("У пользователя, которого вы хотите добавить,"+
                    " дата рождения в будущем времени.");
        }
    }

}
