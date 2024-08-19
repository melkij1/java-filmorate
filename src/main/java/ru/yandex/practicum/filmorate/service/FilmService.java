package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class FilmService {
    private final FilmStorage filmStorage;
    private final UserService userService;

    public List<Film> findAll() {
        return filmStorage.findAll();
    }

    public Film findById(int id) {
        return filmStorage.findById(id).orElseThrow(() -> new FilmNotFoundException("Фильм не найден"));
    }

    public Film create(Film film) {
        return filmStorage.create(film);
    }

    public Film update(Film film) {
        return filmStorage.update(film);
    }

    public void addLike(int filmId, int userId) {
        if (!findById(filmId).getLikes().contains(userId)) {
            throw new UserNotFoundException("Пользователь уже поставил лайк");
        }
        findById(filmId).getLikes().add(userId);
    }

    public void removeLike(int filmId, int userId) {
        final Film film = findById(filmId);
        final User user = userService.findById(userId);

        if (!film.getLikes().contains(userId)) {
            throw new UserNotFoundException("Пользователь не ставил лайк");
        }
        film.getLikes().remove(userId);
    }

    public List<Film> findPopular(int count) {
        return filmStorage.findAll().stream()
                .sorted((film1, film2) -> Integer.compare(film2.getLikes().size(),film1.getLikes().size()))
                .limit(count)
                .collect(Collectors.toList());
    }
}
