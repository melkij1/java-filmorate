package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private static int generatedId = 0;
    private final Map<Integer, User> users = new HashMap<>();

    @GetMapping
    public List<User> getUsers() {
        return new ArrayList<>(users.values());
    }

    @PostMapping
    public User createUser(@Valid @RequestBody User user) {
        validateUser(user);
        user.setId(++generatedId);
        users.put(user.getId(), user);
        log.debug("Пользователь {} создан", user);
        return user;
    }

    @PutMapping()
    public User updateUser(@Valid @RequestBody User user) {
        int id = user.getId();
        if (!users.containsKey(id)) {
            log.debug("Пользователь {} не найден", user);
            throw new ValidationException("Пользователь не найден");
        }
        validateUser(user);
        users.put(id, user);
        return user;
    }


    private void validateUser(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
    }

}
