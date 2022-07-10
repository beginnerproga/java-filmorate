package ru.yandex.practicum.filmorate.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
public class User {

   transient  private Integer id;
    @EqualsAndHashCode.Include

    private String login;
    @EqualsAndHashCode.Include

    private String name;
    @EqualsAndHashCode.Include

    private String email;
    @EqualsAndHashCode.Include

    private LocalDate birthday;

 public User(User user) {
  this.email = user.email;
  this.birthday =user.birthday;
  this.login = user.login;
  this.name = name;
   }
}
