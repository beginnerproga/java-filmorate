package ru.yandex.practicum.filmorate.storage;
import ru.yandex.practicum.filmorate.models.User;
import java.util.HashMap;
import java.util.List;

public interface UserStorage {
    User update(User user);

    User get(int UserId);

    HashMap<Integer, User> getUsers();

    User save(User user);

    void deleteFriend(User user, User friend);

    void addFriend(User user, User friend);

    List<User> getUserFriends(int id);

    List<User> getMutualFriends(User user, User otherUser);
}

