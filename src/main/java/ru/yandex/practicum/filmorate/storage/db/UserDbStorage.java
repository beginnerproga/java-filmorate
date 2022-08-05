package ru.yandex.practicum.filmorate.storage.db;

import ru.yandex.practicum.filmorate.models.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.ArrayList;
import java.util.HashMap;

public class UserDbStorage implements UserStorage {
    @Override
    public User update(User user) {
        return null;
    }

    @Override
    public User get(int UserId) {
        return null;
    }

    @Override
    public HashMap<Integer, User> getUsers() {
        return null;
    }

    @Override
    public User save(User user) {
        return null;
    }

    @Override
    public void deleteFriend(User user, User friend) {

    }

    @Override
    public void addFriend(User user, User friend) {

    }

    @Override
    public ArrayList<User> getUserFriends(User user) {
        return null;
    }

    @Override
    public ArrayList<User> getMutualFriends(User user, User otherUser) {
        return null;
    }
}
