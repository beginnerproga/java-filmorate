package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.models.Mpa;

import java.util.Set;

public interface MpaStorage {
    Set<Mpa> getMpa();
    Mpa getMpaById(int id);

}
