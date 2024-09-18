package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.FriendDbStorage;
import ru.yandex.practicum.filmorate.dao.UserDbStorage;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserService {
    private final UserStorage userStorage;
    private final UserDbStorage userDbStorage;
    private final FriendDbStorage friendDbStorage;

    public List<User> findAll() {
        List<User> users = userStorage.findAll();
        log.debug("Пользователи: {}", users);
        return users;
    }

    public User findById(int id) {
        log.debug("Поиск пользователь с id: {}", id);
        return userDbStorage.findById(id).orElseThrow(() -> new UserNotFoundException("Пользователь не найден"));
    }

    public User create(User user) {
        validateUser(user);
        log.debug("Создание пользователя: {}", user);
        return userDbStorage.create(user);
    }

    public User update(User user) {
        validateUser(user);
        User findUser = findById(user.getId());
        if (findUser == null) {
            throw new UserNotFoundException("Пользователь не найден");
        }
        log.debug("Обновление пользователя: {}", user);
        return userDbStorage.update(user);
    }

    public void addFriend(int userId, int friendId) {
        if (userStorage.findById(userId).isEmpty() || userStorage.findById(friendId).isEmpty()) {
            throw new UserNotFoundException("Пользователь не найден.");
        }
        if (userId < 0 || friendId < 0) {
            throw new UserNotFoundException("Пользователь не найден.");
        }
        log.debug("Добавление дружбы: {} и {}", userId, friendId);
        friendDbStorage.addFriend(userId, friendId);
    }

    public void deleteFriend(int userId, int friendId) {
        if (userStorage.findById(userId).isEmpty() || userStorage.findById(friendId).isEmpty()) {
            throw new UserNotFoundException("Пользователь не найден.");
        }
        log.debug("Удаление дружбы: {} и {}", userId, friendId);
        friendDbStorage.removeFriend(userId, friendId);
    }

    public List<User> findAllFriends(int userId) {
        Integer user = userDbStorage.findCount(userId);
        if (user > 0) {
            log.debug("Поиск всех друзей: {}", userId);
            return friendDbStorage.findAllFriends(userId);
        } else {
            throw new UserNotFoundException("Пользователь не найден");
        }
    }

    public List<User> findCommonFriends(int userId, int friendId) {
        List<User> friends = friendDbStorage.findCommonFriends(userId, friendId);
        log.debug("Поиск общих друзей: {}", userId);
        return friends;
    }

    private void validateUser(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
    }
}
