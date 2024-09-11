package ru.yandex.practicum.filmorate.exception;

public class UserNotFoundDeleteException extends RuntimeException {
    public UserNotFoundDeleteException(String message) {
        super(message);
    }
}
