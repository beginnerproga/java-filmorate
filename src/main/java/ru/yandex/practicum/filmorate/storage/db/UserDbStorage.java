package ru.yandex.practicum.filmorate.storage.db;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.models.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Repository
@Getter
public class UserDbStorage implements UserStorage {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User update(User user) {
        String sqlQuery = "UPDATE users SET user_name = ?, user_login = ?, user_email = ?, user_birthday =? WHERE user_id = ? ";
        jdbcTemplate.update(sqlQuery,
                user.getName(),
                user.getLogin(),
                user.getEmail(),
                user.getBirthday(),
                user.getId());
        return user;
    }

    @Override
    public User get(int userId) {
        String sqlQuery = "SELECT * FROM users where user_id = ?";
        final List<User> users = jdbcTemplate.query(sqlQuery, this::makeUser, userId);
        if (users.size() != 1)
            return null;
        return users.get(0);
    }

    public boolean delete(int userId) {
        String sqlQuery = "DELETE FROM users where user_id = ?";
        return (jdbcTemplate.update(sqlQuery, userId) > 0);
    }

    @Override
    public HashMap<Integer, User> getUsers() {
        String sqlQuery = "SELECT * FROM users";
        final List<User> users = jdbcTemplate.query(sqlQuery, this::makeUser);
        final HashMap<Integer, User> map = new HashMap<>();
        for (User user : users) {
            map.put(user.getId(), user);
        }
        return map;
    }

    private User makeUser(ResultSet rs, int rowNum) throws SQLException {
        return new User(
                rs.getInt("user_id"),
                rs.getString("user_login"),
                rs.getString("user_name"),
                rs.getString("user_email"),
                rs.getDate("user_birthday").toLocalDate()
        );
    }

    @Override
    public User save(User user) {
        String sqlQuery = "INSERT INTO users(user_email, user_login, user_name, user_birthday) VALUES(?,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sqlQuery, new String[]{"user_id"});
            stmt.setString(1, user.getEmail());
            stmt.setString(2, user.getLogin());
            stmt.setString(3, user.getName());
            stmt.setDate(4, Date.valueOf(user.getBirthday()));
            return stmt;
        }, keyHolder);
        user.setId(Objects.requireNonNull(keyHolder.getKey()).intValue());
        return user;
    }

    @Override
    public void deleteFriend(User user, User friend) {
        String sqlQuery = "DELETE FROM user_friends WHERE USER_ID =? and FRIEND_ID = ?";
        jdbcTemplate.update(sqlQuery,
                user.getId(),
                friend.getId());
    }

    @Override
    public void addFriend(User user, User friend) {
        String sqlQuery = "INSERT INTO user_friends(user_id, friend_id, status) VALUES(?,?,?)";
        jdbcTemplate.update(sqlQuery,
                user.getId(),
                friend.getId(),
                true);
        jdbcTemplate.update(sqlQuery,
                friend.getId(),
                user.getId(),
                false);
    }

    @Override
    public List<User> getUserFriends(int id) {
        String sqlQuery = "SELECT users.user_id, users.user_name,users.user_login, users.user_email,users.user_birthday " +
                "FROM users WHERE users.user_id IN" +
                "((SELECT friend_id FROM user_friends WHERE user_id = ? and status = ?))";
        return jdbcTemplate.query(sqlQuery, this::makeUser, id, true);
    }

    @Override
    public List<User> getMutualFriends(User user, User otherUser) {
        String sqlQuery = "SELECT * FROM users WHERE user_id = (" +
                "SELECT friend_id FROM user_friends WHERE user_id = ?" +
                "INTERSECT SELECT friend_id FROM user_friends WHERE user_id = ?)";
        return jdbcTemplate.query(sqlQuery, this::makeUser, user.getId(), otherUser.getId());
    }
}
