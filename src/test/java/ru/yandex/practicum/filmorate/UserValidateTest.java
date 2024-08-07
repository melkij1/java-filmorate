package ru.yandex.practicum.filmorate;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class UserValidateTest {
    private User user;
    private static Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void validateUserEmailIsEmpty() {
        user = new User();
        user.setId(1);
        user.setEmail("");
        user.setLogin("yandex");
        user.setName("Yandex");
        user.setBirthday(LocalDate.of(1994,8,10));

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size());
        assertEquals("Электронная почта не может быть пустой", violations.iterator().next().getMessage());
    }

    @Test
    void validateUserEmailIsRegexp() {
        user = new User();
        user.setId(1);
        user.setEmail("yandex.ru");
        user.setLogin("yandex");
        user.setName("Yandex");
        user.setBirthday(LocalDate.of(1994,8,10));

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size());
        assertEquals("Электронная почта должна содержать символ @", violations.iterator().next().getMessage());
    }

    @Test
    void validateUserLoginInSpaces() {
        user = new User();
        user.setId(1);
        user.setEmail("123@yandex.ru");
        user.setLogin("yan dex");
        user.setName("Yandex");
        user.setBirthday(LocalDate.of(1994,8,10));

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size());
        assertEquals("Логин не может содержать пробелы.", violations.iterator().next().getMessage());
    }

    @Test
    void validateUserLoginBirthDay() {
        user = new User();
        user.setId(1);
        user.setEmail("123@yandex.ru");
        user.setLogin("yandex");
        user.setName("Yandex");
        user.setBirthday(LocalDate.MAX);

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size());
        assertEquals("Дата рождения не может быть в будущем.", violations.iterator().next().getMessage());
    }
}
