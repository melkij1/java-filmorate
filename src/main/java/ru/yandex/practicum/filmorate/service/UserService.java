package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.FriendDbStorage;
import ru.yandex.practicum.filmorate.dao.UserDbStorage;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserStorage userStorage;
    private final UserDbStorage userDbStorage;
    private final FriendDbStorage friendDbStorage;

    public List<User> findAll() {
        return userDbStorage.findAll();
    }

    public User findById(int id) {
        return userDbStorage.findById(id).orElseThrow(() -> new UserNotFoundException("Пользователь не найден"));
    }

    public User create(User user) {
        validateUser(user);
        return userDbStorage.create(user);
    }

    public User update(User user) {
        validateUser(user);
        User findUser = findById(user.getId());
        if(findUser == null) {
            throw new UserNotFoundException("Пользователь не найден");
        }
        return userDbStorage.update(user);
    }

    public void addFriend(int userId, int friendId) {
        Integer user = userDbStorage.findCount(userId);
        Integer friend = userDbStorage.findCount(friendId);
        if (user > 0 && friend > 0) {
            friendDbStorage.addFriend(userId, friendId, 1);

        }else{
            throw new UserNotFoundException("Пользователь не найден");
        }
    }

    public void deleteFriend(int userId, int friendId) {
//        friendDbStorage.removeFriend(userId, friendId);
        Integer user = userDbStorage.findCount(userId);
        Integer friend = userDbStorage.findCount(friendId);
        if (user > 0 && friend > 0) {
            friendDbStorage.removeFriend(userId, friendId);
            friendDbStorage.removeFriend(friendId, userId);

        }else{
            throw new UserNotFoundException("Пользователь не найден");
        }
//        findById(userId).getFriends().remove(friendId);
//        findById(friendId).getFriends().remove(userId);
    }

    public List<User> findAllFriends(int userId) {
        Integer user = userDbStorage.findCount(userId);
        if (user > 0) {
            return friendDbStorage.findAllFriends(userId);
        }else{
            throw new UserNotFoundException("Пользователь не найден");
        }
    }

    public List<User> findCommonFriends(int userId, int friendId) {
//        List<User> commonFriends = findAllFriends(userId);
//        List<User> commonFriendsSecond = findAllFriends(friendId);
//        commonFriends.retainAll(commonFriendsSecond);
//        return commonFriends;
        return friendDbStorage.findCommonFriends(userId, friendId);
    }

    private void validateUser(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
    }
}
