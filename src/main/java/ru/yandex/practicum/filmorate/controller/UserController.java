package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<User> findAll() {
        return userService.findAll();
    }

    @PostMapping
    public User createUser(@Valid @RequestBody User user) {
        log.debug("Пользователь {} создан", user.getLogin());
        userService.create(user);
        return user;
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user) {
        log.debug("Пользователь {} изменен", user.getLogin());
        userService.update(user);
        return user;
    }

    @GetMapping("/{id}")
    public User findById(@PathVariable int id) {
        log.debug("Поиск пользователя по id {}", id);
        return userService.findById(id);
    }

    @GetMapping("/{id}/friends")
    public List<User> findFriends(@PathVariable int id) {
        log.debug("Поиск друзей пользователя по id {}", id);
        return userService.findAllFriends(id);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public void addFriend(@PathVariable int id, @PathVariable int friendId) {
        log.debug("Добавление друга {} пользователю {}", friendId, id);
        userService.addFriend(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable int id, @PathVariable int friendId) {
        log.debug("Удаление друга {} пользователю {}", friendId, id);
        userService.deleteFriend(id, friendId);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> findCommonFriends(@PathVariable int id, @PathVariable int otherId) {
        log.debug("Поиск общих друзей пользователя {} и пользователя {}", id, otherId);
        return userService.findCommonFriends(id, otherId);
    }
}
