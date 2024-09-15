package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/mpa")
public class RatingController {
    private final FilmService filmService;

    @GetMapping
    public List<Genre> findAllGenres() {
        return filmService.findAllGenres();
    }

    @GetMapping("/{id}")
    public Optional<Genre> findGenreById(@PathVariable("id") int id) {
        return filmService.findGenreById(id);
    }
}
