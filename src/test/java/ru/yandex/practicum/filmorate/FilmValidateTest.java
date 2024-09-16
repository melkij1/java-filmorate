package ru.yandex.practicum.filmorate;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class FilmValidateTest {
    private Film film;
    private static Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void validateFilmName() {
        film = Film.builder()
                .id(1)
                .name("")
                .description("Описание фильма")
                .releaseDate(LocalDate.of(2002, 2, 2))
                .duration(100)
                .mpa(new Mpa(1, "G"))
                .build();
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertEquals(1, violations.size());
        assertEquals("Введите название фильма", violations.iterator().next().getMessage());
    }

    @Test
    void validateFildDescription() {
        film = Film.builder()
                .id(1)
                .name("Film")
                .description("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut" +
                        "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut")
                .releaseDate(LocalDate.of(2000, 8, 2))
                .duration(100)
                .mpa(new Mpa(1, "G"))
                .build();
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertEquals(1, violations.size());
        assertEquals("Не более 200 символов", violations.iterator().next().getMessage());
    }

    @Test
    void validateFilmReleaseDate() {
        film = Film.builder()
                .id(1)
                .name("Film")
                .description("Описание фильма")
                .releaseDate(LocalDate.of(1895, 12, 27))
                .duration(100)
                .mpa(new Mpa(1, "G"))
                .build();
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertEquals(1, violations.size());
        assertEquals("Введите дату релиза фильма не ранее 28 декабря 1895 года.",
                violations.iterator().next().getMessage());
    }

    @Test
    void validateFilmDuration() {
        film = Film.builder()
                .id(1)
                .name("Film")
                .description("Описание фильма")
                .releaseDate(LocalDate.of(1895, 12, 29))
                .duration(-100)
                .mpa(new Mpa(1, "G"))
                .build();
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertEquals(1, violations.size());
        assertEquals("Продолжительность фильма должна быть больше 0",
                violations.iterator().next().getMessage());
    }
}
