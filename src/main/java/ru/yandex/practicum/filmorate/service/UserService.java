package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ErrorIdException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.models.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

@Service
public class UserService {
    @Autowired
    UserStorage userStorage;

    public User get(int userId) {
        User user = userStorage.get(userId);
        if (user == null) {
            throw new NotFoundException("User с id = " + userId + " не найден.");
        }
        return user;
    }

    public User save(User user) {
        return userStorage.save(user);
    }

    public ArrayList<User> getUsers() {
        return new ArrayList<User>(userStorage.getUsers().values());
    }

    public User update(User user) {

        if (user.getId() <= 0 || user.getId() > userStorage.getUsers().keySet().stream().max(new Comparator<Integer>() {

            @Override
            public int compare(Integer n1, Integer n2) {
                return n1.compareTo(n2);
            }
        }).get()) {
            throw new ErrorIdException("Введен неккоретный  User id = " + user.getId());
        }
        return userStorage.update(user);
    }

    public void deleteFriend(int id, int friendId) {
        User user = userStorage.get(id);
        User friend = userStorage.get(friendId);
        if (user == null) {
            throw new NotFoundException("User с id = " + id + " не найден.");
        } else if (friend == null) {
            throw new NotFoundException("User с id = " + friendId + " не найден.");
        }
        userStorage.deleteFriend(user, friend);

    }

    public void addFriend(int id, int friendId) {
        User user = userStorage.get(id);
        User friend = userStorage.get(friendId);
        if (user == null) {
            throw new NotFoundException("User с id = " + id + " не найден.");
        } else if (friend == null) {
            throw new NotFoundException("User с id = " + friendId + " не найден.");
        }
        userStorage.addFriend(user, friend);

    }

    public ArrayList<User> getUserFriends(int id) {
        User user = userStorage.get(id);
        if (user == null) {
            throw new NotFoundException("User с id = " + id + " не найден.");
        }
        return userStorage.getUserFriends(user);

    }

    public ArrayList<User> getMutualFriends(int id, int otherId) {
        User user = userStorage.get(id);
        User otherUser = userStorage.get(otherId);
        if (user == null) {
            throw new NotFoundException("User с id = " + id + " не найден.");
        } else if (otherUser == null) {
            throw new NotFoundException("User с id = " + otherId + " не найден.");
        }
        return userStorage.getMutualFriends(user, otherUser);

    }
}