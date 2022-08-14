package ru.yandex.practicum.filmorate.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor

public class Film implements Comparable<Film> {
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
    private Set<Genre> genres = new HashSet<>();
    @NotNull
    @EqualsAndHashCode.Include
    private Mpa mpa;
    @NotNull
    @EqualsAndHashCode.Include
    private int rate;
    @JsonIgnore
    Set<Integer> usersId = new HashSet<>();

    public Film(Integer id, String name, String description, LocalDate releaseDate, int duration, Set<Genre> genres, Mpa mpa, int rate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.genres = genres;
        this.mpa = mpa;
        this.rate = rate;
    }
    public Film( String name, String description, LocalDate releaseDate, int duration, Set<Genre> genres, Mpa mpa, int rate) {
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.genres = genres;
        this.mpa = mpa;
        this.rate = rate;
    }
    public Film(Integer id, String name, String description, LocalDate releaseDate, int duration, Mpa mpa, int rate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.mpa = mpa;
        this.rate = rate;
    }
    public int compareTo(Film p) {
        return this.usersId.size() - p.usersId.size();

    }
}
