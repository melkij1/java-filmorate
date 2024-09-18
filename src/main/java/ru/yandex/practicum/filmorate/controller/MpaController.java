package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/mpa")
public class MpaController {
    private final FilmService filmService;

    @GetMapping
    public List<Mpa> findAllMpas() {
        return filmService.findAllMpa();
    }

    @GetMapping("/{id}")
    public Optional<Mpa> findMpaById(@PathVariable("id") int id) {
        return filmService.findMpaById(id);
    }
}
