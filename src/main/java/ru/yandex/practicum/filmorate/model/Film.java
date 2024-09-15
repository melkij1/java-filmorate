package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;
import ru.yandex.practicum.filmorate.annotation.ReleaseDate;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;


@Data
@Builder
public class Film {
    private Integer id;
    @NotBlank(message = "Введите название фильма")
    private String name;
    @NotNull
    @Size(max = 200, message = "Не более 200 символов")
    private String description;
    @NotNull
    @ReleaseDate(value = "1895-12-28", message = "Введите дату релиза фильма не ранее 28 декабря 1895 года.")
    private LocalDate releaseDate;
    @Positive(message = "Продолжительность фильма должна быть больше 0")
    private Integer duration;
    private final Set<Integer> likes = new HashSet<>();
    private Mpa mpa;
    private final LinkedHashSet<Genre> genres = new LinkedHashSet<>();
}