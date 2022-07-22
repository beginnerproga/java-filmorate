package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.models.User;

import java.util.ArrayList;

public interface UserService {
    User get(int userId);
    User save(User user);
    ArrayList<User> getUsers();
    User update(User user);
    void deleteFriend(int id, int friendId);
    void addFriend(int id, int friendId);
    ArrayList<User> getUserFriends(int id);
    ArrayList<User> getMutualFriends(int id, int otherId);

}
