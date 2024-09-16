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
        user = User.builder()
                .id(1)
                .email("")
                .login("ya")
                .name("Mihail")
                .birthday(LocalDate.of(1986, 11, 15))
                .build();
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size());
        assertEquals("Электронная почта не может быть пустой", violations.iterator().next().getMessage());
    }

    @Test
    void validateUserEmailIsRegexp() {
        user = User.builder()
                .id(1)
                .email("123ya.ru")
                .login("ya")
                .name("Mihlail")
                .birthday(LocalDate.of(1986, 11, 15))
                .build();
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size());
        assertEquals("Электронная почта должна содержать символ @", violations.iterator().next().getMessage());
    }

    @Test
    void validateUserLoginBirthDay() {
        user = User.builder()
                .id(1)
                .email("123@ya.ru")
                .login("ya")
                .name("Ivan")
                .birthday(LocalDate.MAX)
                .build();
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size());
        assertEquals("Дата рождения не может быть в будущем.",
                violations.iterator().next().getMessage());
    }
}
