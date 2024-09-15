package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
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
public class FilmService {
    private final FilmDbStorage filmDbStorage;
    private final LikeDbStorage likeDbStorage;
    private final UserDbStorage userDbStorage;
    private final RatingDbStorage ratingDbStorage;
    private final GenreDbStorage genreDbStorage;

    public List<Film> findAll() {
        return filmDbStorage.findAll();
    }

    public Film findById(int id) {
        return filmDbStorage.findById(id).orElseThrow(() -> new FilmNotFoundException("Фильм не найден"));
    }

    public Film create(Film film) {
        return filmDbStorage.create(film);
    }

    public Film update(Film film) {
        Integer filmCount = filmDbStorage.findCount(film.getId());
        if (filmCount > 0) {
            return filmDbStorage.update(film);
        }else{
            throw new FilmNotFoundException("Фильм не найден");
        }
    }

    public void addLike(int filmId, int userId) {
        Optional<Film> film = filmDbStorage.findById(filmId);
        Optional<User> user = userDbStorage.findById(userId);
        if (film.isEmpty()) {
            throw new FilmNotFoundException("Не найден фильм, у которого устанавливается лайк");
        } else if (user.isEmpty()) {
            throw new UserNotFoundException("Не найден пользователь, чей устанавливается лайк");
        } else if (findById(filmId).getLikes().contains(userId)) {
            throw new UserNotFoundException("Пользователь уже поставил лайк");
        } else {
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
        } else likeDbStorage.removeLike(filmId, userId);

    }

    public List<Film> findPopular(int count) {

        if (count <= 0) {
            throw new ValidationException("Количество фильмов должно быть больше 0");
        }

        return filmDbStorage.findPopular(count);
    }

    public List<Mpa> findAllRatings() {
        return ratingDbStorage.findAllRatings();
    }

    public Optional<Mpa> findRatingById(int id) {
        return ratingDbStorage.findRatingById(id);
    }

    public List<Genre> findAllGenres() {
        return genreDbStorage.findAll();
    };

    public Optional<Genre> findGenreById(int id) {
        return genreDbStorage.findById(id);
    };
}
