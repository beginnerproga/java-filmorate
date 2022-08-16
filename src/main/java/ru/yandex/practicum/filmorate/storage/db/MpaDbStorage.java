package ru.yandex.practicum.filmorate.storage.db;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.models.Mpa;
import ru.yandex.practicum.filmorate.storage.MpaStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Repository
@RequiredArgsConstructor
public class MpaDbStorage implements MpaStorage {
    private final JdbcTemplate jdbcTemplate;

    private Mpa makeMpa(ResultSet resultSet, int rowNum) throws SQLException {
        return new Mpa(
                resultSet.getInt("mpa.mpa_id"), resultSet.getString("mpa.mpa"));
    }

    public Set<Mpa> getMpa() {
        String sqlQuery = "Select * FROM mpa ORDER BY mpa_id";
        return new LinkedHashSet<>(jdbcTemplate.query(sqlQuery, this::makeMpa));
    }

    public Mpa getMpaById(int id) {
        String sqlQuery = "SELECT mpa.mpa_id, mpa.mpa  FROM mpa WHERE mpa.mpa_id = ?";
        final List<Mpa> mpa = new ArrayList<>(jdbcTemplate.query(sqlQuery, this::makeMpa, id));
        return mpa.get(0);
    }
}
