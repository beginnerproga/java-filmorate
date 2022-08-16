package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.models.User;

import java.util.ArrayList;
import java.util.List;

public interface UserService {
    User get(int userId);
    User save(User user);
    ArrayList<User> getUsers();
    User update(User user);
    void deleteFriend(int id, int friendId);
    void addFriend(int id, int friendId);
    List<User> getUserFriends(int id);
    List<User> getMutualFriends(int id, int otherId);

}
