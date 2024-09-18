package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.*;
import ru.yandex.practicum.filmorate.exception.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class FilmService {
    private final FilmDbStorage filmDbStorage;
    private final LikeDbStorage likeDbStorage;
    private final UserDbStorage userDbStorage;
    private final MpaDbStorage ratingDbStorage;
    private final GenreDbStorage genreDbStorage;

    public List<Film> findAll() {
        List<Film> films = filmDbStorage.findAll();
        log.debug("Все фильмы: {}", films);
        return films;
    }

    public Film findById(int id) {
        Film film = filmDbStorage.findById(id).orElseThrow(() -> new FilmNotFoundException("Фильм не найден"));
        genreDbStorage.findAllGenresByFilm(List.of(film));
        log.debug("Фильм: {}", film);
        return film;
    }

    public Film create(Film film) {
        Optional<Mpa> mpa = ratingDbStorage.findMpaById(film.getMpa().getId());
        film.getGenres().forEach(genre -> {
            Optional<Genre> g = genreDbStorage.findById(genre.getId());
            if (g.isEmpty()) {
                throw new GenreNotFoundException("Не найден жанр");
            }
        });
        if (mpa.isEmpty()) {
            throw new MpaNotFoundException("Не найдена оценка");
        }
        log.debug("Создание фильма: {}", film);
        return filmDbStorage.create(film);
    }

    public Film update(Film film) {
        Integer filmCount = filmDbStorage.findCount(film.getId());
        if (filmCount > 0) {
            log.debug("Обновление фильма: {}", film);
            return filmDbStorage.update(film);
        } else {
            throw new FilmNotFoundException("Фильм не найден");
        }
    }

    public void addLike(int filmId, int userId) {
        if (filmDbStorage.findById(filmId).isEmpty() || userDbStorage.findById(userId).isEmpty()) {
            throw new ValidationException("Не найден фильм или пользователь");
        } else {
            log.debug("Добавление лайка к фильму: {}", filmId);
            likeDbStorage.addLike(filmId, userId);
        }
    }

    public void removeLike(int filmId, int userId) {
        Optional<Film> film = filmDbStorage.findById(filmId);
        Optional<User> user = userDbStorage.findById(userId);
        if (film.isEmpty()) {
            throw new FilmNotFoundException("Не найден фильм, у которого удаляется лайк");
        } else if (user.isEmpty()) {
            throw new UserNotFoundException("Не найден пользователь, чей удаляется лайк");
        } else {
            log.debug("Удаление лайка к фильму: {}", filmId);
            likeDbStorage.removeLike(filmId, userId);
        }

    }

    public List<Film> findPopular(int count) {
        if (count <= 0) {
            throw new ValidationException("Количество фильмов должно быть больше 0");
        }
        List<Film> films = filmDbStorage.findPopular(count);
        genreDbStorage.findAllGenresByFilm(films);
        log.debug("Популярные фильмы: {}", films);
        return films;
    }

    public List<Mpa> findAllMpa() {
        List<Mpa> mpaList = ratingDbStorage.findAllMpa();
        log.debug("Все оценки: {}", mpaList);
        return mpaList;
    }

    public Optional<Mpa> findMpaById(int id) {
        Optional<Mpa> mpa = ratingDbStorage.findMpaById(id);
        if (mpa.isPresent()) {
            log.debug("Оценка: {}", mpa);
            return mpa;
        } else {
            throw new ValidationException("Не найдена оценка");
        }
    }

    public List<Genre> findAllGenres() {
        List<Genre> genres = genreDbStorage.findAll();
        log.debug("Жанры: {}", genres);
        return genres;
    }

    public Optional<Genre> findGenreById(int id) {
        Optional<Genre> genre = genreDbStorage.findById(id);
        if (genre.isPresent()) {
            log.debug("Жанр: {}", genre);
            return genre;
        } else {
            throw new ValidationException("Не найден жанр");
        }
    }
}
