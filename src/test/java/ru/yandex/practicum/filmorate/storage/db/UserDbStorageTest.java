package ru.yandex.practicum.filmorate.storage.db;


import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.models.User;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Optional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)

class UserDbStorageTest {
    private final UserDbStorage userStorage ;
    @Test
    void testSave() {
        User user = new User("fewf", "wdew", "mail@mail.ru", LocalDate.of(2002, 3, 12));
        assertThat(userStorage.save(user)).hasFieldOrPropertyWithValue("id", 3);
        User userCheck = userStorage.get(3);
        assertEquals("wdew", userCheck.getName());
        assertEquals("fewf", userCheck.getLogin());
        assertEquals("mail@mail.ru", userCheck.getEmail());
        assertEquals(LocalDate.of(2002, 3,12), userCheck.getBirthday());
    }
    @Test
    void testUpdate() {
        User user = new User(2,"new", "class", "mailka@mail.ru", LocalDate.of(2002, 3, 12));
        assertThat(userStorage.update(user)).hasFieldOrPropertyWithValue("id", 2);
        User userCheck = userStorage.get(2);
        assertEquals("class", userCheck.getName());
        assertEquals("new", userCheck.getLogin());
        assertEquals("mailka@mail.ru", userCheck.getEmail());
        assertEquals(LocalDate.of(2002, 3,12), userCheck.getBirthday());

    }

    @Test
    void testGet() {
        User user = userStorage.get(1);
        assertEquals("Nikita", user.getName());
        assertEquals("qwerty", user.getLogin());
        assertEquals("maifwrfwefwl@mail.ru", user.getEmail());
        assertEquals(LocalDate.of(2022, 2, 3), user.getBirthday());

        Optional<User> userOptional = Optional.of(user);
        assertThat(userOptional)
                .isPresent()
                .hasValueSatisfying(x ->
                        assertThat(x).hasFieldOrPropertyWithValue("id", 1)
                );
    }
    @Test
    void testDelete() {
       assertTrue(userStorage.delete(3));
       User user = userStorage.get(3);
       assertNull(user);
    }

    @Test
    void testGetUsers() {
        HashMap<Integer, User> users = userStorage.getUsers();
        assertEquals(2, users.size());
    }

    }
