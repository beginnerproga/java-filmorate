package ru.yandex.practicum.filmorate.models;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(of = "id")
public class Genre {
    @NotNull
    private int id;
    private String name;

    public Genre(int id) {
        this.id = id;
    }
}
