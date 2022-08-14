package ru.yandex.practicum.filmorate.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ErrorIdException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.models.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserStorage userStorage;

    @Autowired
    public UserServiceImpl(@Qualifier("userDbStorage") UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    @Override
    public User get(int userId) {
        User user = userStorage.get(userId);
        if (user == null) {
            throw new NotFoundException("User с id = " + userId + " не найден.");
        }
        return user;
    }

    @Override
    public User save(User user) {
        return userStorage.save(user);
    }

    @Override
    public ArrayList<User> getUsers() {
        return new ArrayList<>(userStorage.getUsers().values());
    }

    @Override
    public User update(User user) {

        if (user.getId() <= 0 || user.getId() > userStorage.getUsers().keySet().stream().max(Integer::compare).get())  // Optional<Integer>

        {
            throw new ErrorIdException("Введен неккоретный  User id = " + user.getId());
        }
        return userStorage.update(user);
    }

    @Override
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

    @Override
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

    @Override
    public List<User> getUserFriends(int id) {
        User user = userStorage.get(id);
        if (user == null) {
            throw new NotFoundException("User с id = " + id + " не найден.");
        }
        return userStorage.getUserFriends(id);

    }

    @Override
    public List<User> getMutualFriends(int id, int otherId) {
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