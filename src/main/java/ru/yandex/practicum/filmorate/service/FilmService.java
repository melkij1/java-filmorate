package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

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
        Optional<Film> film = filmStorage.findById(filmId);
        if (film.isEmpty()) {
            throw new FilmNotFoundException("Не найден фильм, у которого удаляется лайк");
        } else if (userStorage.findById(userId).isEmpty()) {
            throw new UserNotFoundException("Не найден пользователь, чей удаляется лайк");
        } else if (filmId < 0 || userId < 0) {
            throw new ValidationException("Некорректный формат переданных параметров");
        } else {
            film.get().getLikes().remove(userId);
        }
//        final Film film = findById(filmId);
//        final User user = userService.findById(userId);
//
//        if (!film.getLikes().contains(userId)) {
//            throw new UserNotFoundException("Пользователь не ставил лайк");
//        }
//        film.getLikes().remove(userId);
    }

    public List<Film> findPopular(int count) {
        return filmStorage.findAll().stream()
                .sorted((film1, film2) -> Integer.compare(film2.getLikes().size(),film1.getLikes().size()))
                .limit(count)
                .collect(Collectors.toList());
    }
}
