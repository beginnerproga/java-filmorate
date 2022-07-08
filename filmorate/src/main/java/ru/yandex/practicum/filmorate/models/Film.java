package ru.yandex.practicum.filmorate.models;

import lombok.*;

import java.time.LocalDate;
@Data
public class Film {
    private Integer id;
    private String name;
    private String description;
    private LocalDate releaseDate;
    private int duration;
}
