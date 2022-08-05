package ru.yandex.practicum.filmorate.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor

public class Film  implements Comparable<Film>{

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
    @EqualsAndHashCode.Include
    private Genre genre;
    @EqualsAndHashCode.Include
    private MPA mpa;


    public Set<Integer> getUsersId() {
        return usersId;
    }

    @JsonIgnore
    Set<Integer> usersId = new HashSet<>();

    public int compareTo(Film p){
        return this.usersId.size() - p.usersId.size();

    }
}
