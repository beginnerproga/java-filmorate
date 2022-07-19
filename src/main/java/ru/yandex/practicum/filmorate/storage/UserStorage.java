package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.models.User;

import java.util.ArrayList;
import java.util.HashMap;

public interface UserStorage {
    User update(User user);

    User get(int UserId);

    HashMap<Integer, User> getUsers();

    User save(User user);

    void deleteFriend(User user, User friend);

    void addFriend(User user, User friend);

    ArrayList<User> getUserFriends(User user);

    ArrayList<User> getMutualFriends(User user, User otherUser);
}

