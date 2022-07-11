package ru.yandex.practicum.filmorate.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Integer id;
    @EqualsAndHashCode.Include
    @NotNull
    private String login;
    @EqualsAndHashCode.Include

    private String name;
    @EqualsAndHashCode.Include
    @NotNull
    @Email
    private String email;
    @EqualsAndHashCode.Include
    private LocalDate birthday;

}
