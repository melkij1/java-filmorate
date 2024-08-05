package ru.yandex.practicum.filmorate;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.Film;

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
        film = new Film();
        film.setId(1);
        film.setName("");
        film.setDescription("Описание вашего фильма");
        film.setReleaseDate(LocalDate.of(2024,12,5));
        film.setDuration(120);

        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertEquals(1, violations.size());
        assertEquals("Введите название фильма", violations.iterator().next().getMessage());
    }

    @Test
    void validateFildDescription() {
        film = new Film();
        film.setId(1);
        film.setName("Название фильма");
        film.setDescription("Lorem Ipsum - это текст-\"рыба\", часто используемый в печати и вэб-дизайне. Lorem Ipsum является стандартной \"рыбой\" для текстов на латинице с начала XVI века. В то время некий безымянный печатник создал большую коллекцию размеров и форм шрифтов, используя Lorem Ipsum для распечатки образцов. Lorem Ipsum не только успешно пережил без заметных изменений пять веков, но и перешагнул в электронный дизайн. Его популяризации в новое время послужили публикация листов Letraset с образцами Lorem Ipsum в 60-х годах и, в более недавнее время, программы электронной вёрстки типа Aldus PageMaker, в шаблонах которых используется Lorem Ipsum.");
        film.setReleaseDate(LocalDate.of(2024,12,5));
        film.setDuration(120);

        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertEquals(1, violations.size());
        assertEquals("Не более 200 символов", violations.iterator().next().getMessage());
    }

    @Test
    void validateFilmReleaseDate() {
        film = new Film();
        film.setId(1);
        film.setName("Название фильма");
        film.setDescription("Описание вашего фильма");
        film.setReleaseDate(LocalDate.of(1895,12,27));
        film.setDuration(120);

        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertEquals(1, violations.size());
        assertEquals("Введите дату релиза фильма не ранее 28 декабря 1895 года.", violations.iterator().next().getMessage());
    }

    @Test
    void validateFilmDuration() {
        film = new Film();
        film.setId(1);
        film.setName("Название фильма");
        film.setDescription("Описание вашего фильма");
        film.setReleaseDate(LocalDate.of(2024,12,5));
        film.setDuration(-100);

        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertEquals(1, violations.size());
        assertEquals("Продолжительность фильма должна быть больше 0", violations.iterator().next().getMessage());
    }
}
