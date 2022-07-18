package ru.yandex.practicum.filmorate.validation;


import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.controllers.FilmController;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.models.Film;
import ru.yandex.practicum.filmorate.models.User;

import java.time.LocalDate;
@NoArgsConstructor
@Component
public class Validator {
    private final Logger log = LoggerFactory.getLogger(FilmController.class);
    public  void validateInFilmController(Film film) throws ValidationException {

        if (film.getName()==null ||film.getName().isEmpty()){
            log.error("У фильма, который вы хотите добавить, отсутствует название.");
            throw new ValidationException("У фильма, который вы хотите добавить, отсутствует название.");
        }
        if (film.getDescription().length()>200){
            log.error("У фильма, который вы хотите добавить, слишком длинное описание.");
            throw  new ValidationException("У фильма, который вы хотите добавить, слишком длинное описание.");
        }
        if (film.getReleaseDate().isBefore(LocalDate.of(1895,12,28))){
            log.error("У фильма, который вы хотите добавить, дата релиза слишком ранняя.");
            throw new ValidationException("У фильма, который вы хотите добавить, дата релиза слишком ранняя.");
        }
        if (film.getDuration()<=0){
            log.error("У фильма, который вы хотите добавить, отрицательная или нулевая продолжительность.");
            throw new ValidationException("У фильма, который вы хотите добавить, отрицательная или нулевая продолжительность.");
        }
    }
    public void validateInUserController(User user) throws  ValidationException {
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
