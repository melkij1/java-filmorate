package ru.yandex.practicum.filmorate.exception;

public class LikeNotFoundException extends RuntimeException{
    public LikeNotFoundException(String message) {
        super(message);
    }
}
