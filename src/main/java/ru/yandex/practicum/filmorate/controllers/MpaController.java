package ru.yandex.practicum.filmorate.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.models.Mpa;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.Set;

@RestController
@RequestMapping("/mpa")
@RequiredArgsConstructor
@Slf4j
public class MpaController {
    private final FilmService filmService;

    @GetMapping()
    public Set<Mpa> getMpa() {
        return filmService.getMpa();
    }

    @GetMapping("/{id}")
    public Mpa getMpaById(@PathVariable(required = false) int id) {
        return filmService.getMpaById(id);
    }
}
