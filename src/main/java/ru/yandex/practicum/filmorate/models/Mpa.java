package ru.yandex.practicum.filmorate.models;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(of="id")
public class Mpa {
    @NotNull
    private int id;
    private String name;

    public Mpa(int id) {
        this.id = id;
    }
}
