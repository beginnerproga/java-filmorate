package ru.yandex.practicum.filmorate.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor

public class Film {
     private Integer id;
    @EqualsAndHashCode.Include
    @NotNull
    private String name;
    @EqualsAndHashCode.Include
    private String description;
    @EqualsAndHashCode.Include
    private LocalDate releaseDate;
    @EqualsAndHashCode.Include
    @Positive
    private int duration;
}
