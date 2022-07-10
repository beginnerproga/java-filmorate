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

public class Film {
    transient private Integer id;
    @EqualsAndHashCode.Include
    private String name;
    @EqualsAndHashCode.Include
    private String description;
    @EqualsAndHashCode.Include
    private LocalDate releaseDate;
    @EqualsAndHashCode.Include
    private int duration;
    public Film(Film film){
        this.name = film.name;
        this.description = film.description;
        this.releaseDate = film.releaseDate;
        this.duration = film.duration;
    }
}
