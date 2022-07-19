package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.models.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;


@Component
public class InMemoryUserStorage implements UserStorage {
    private final HashMap<Integer, User> users = new HashMap<>();


    private Integer counter = 0;

    public Integer getCounter() {
        return counter;
    }

    @Override
    public User get(int UserId) {
        return users.get(UserId);
    }

    public User save(User user) {
        user.setId(++counter);
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public void deleteFriend(User user, User friend) {
        user.getFriendsId().remove(friend.getId());
        friend.getFriendsId().remove(user.getId());
    }

    @Override
    public void addFriend(User user, User friend) {
        user.getFriendsId().add(friend.getId());
        friend.getFriendsId().add(user.getId());
    }

    @Override
    public ArrayList<User> getUserFriends(User user) {
        ArrayList<User> userFriends = new ArrayList<>();
        for (int id :user.getFriendsId()){
            userFriends.add(users.get(id));
        }
        return userFriends;
    }

    @Override
    public ArrayList<User> getMutualFriends(User user, User otherUser) {
        Set<Integer> result = new HashSet<>(user.getFriendsId());
        result.retainAll(otherUser.getFriendsId());
        ArrayList<User> mutualFriends = new ArrayList<>();
        for (int id: result) {
            mutualFriends.add(users.get(id));
        }
        return mutualFriends;
    }

    @Override
    public HashMap<Integer, User> getUsers() {
        return users;
    }

    @Override
    public User update(User user) {
        users.put(user.getId(), user);
        return user;
    }


}
