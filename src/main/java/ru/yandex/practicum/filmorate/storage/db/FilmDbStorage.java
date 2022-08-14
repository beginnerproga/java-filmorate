package ru.yandex.practicum.filmorate.storage.db;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.models.Film;
import ru.yandex.practicum.filmorate.models.Mpa;
import ru.yandex.practicum.filmorate.models.User;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import java.sql.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Repository
public class FilmDbStorage implements FilmStorage {
    private final JdbcTemplate jdbcTemplate;

    @Autowired

    public FilmDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private Film makeFilm(ResultSet resultSet, int rowNum) throws SQLException {
        Film film = new Film();
        film.setId(resultSet.getInt("films.film_id"));
        film.setName(resultSet.getString("films.film_name"));
        film.setDescription(resultSet.getString("films.film_description"));
        film.setReleaseDate(resultSet.getDate("films.film_release_date").toLocalDate());
        film.setDuration(resultSet.getInt("films.film_duration"));
        film.setRate(resultSet.getInt("films.film_rate"));
        film.setMpa(new Mpa(resultSet.getInt("films.film_mpa"), resultSet.getString("mpa.mpa")));
        return film;
    }

    @Override
    public Film update(Film film) {
        String sqlQuery = "update films set " +
                "film_name = ?, film_description = ?, film_release_date = ? , film_duration = ?, film_rate=?, film_mpa=?" +
                "where film_id = ?";
        jdbcTemplate.update(sqlQuery
                , film.getName()
                , film.getDescription()
                , film.getReleaseDate()
                , film.getDuration()
                , film.getRate()
                , film.getMpa().getId()
                , film.getId()
        );

        return film;
    }


    @Override
    public Film get(int filmId) {
        final String sqlQuery = "SELECT * , mpa.mpa FROM films left outer join mpa ON mpa.mpa_id = films.film_mpa  WHERE films.film_id = ? ";
        final List<Film> films = jdbcTemplate.query(sqlQuery, this::makeFilm, filmId);
        if (films.size() != 1)
            return null;
        return films.get(0);
    }

    @Override
    public HashMap<Integer, Film> getFilms() {
        final String sqlQuery = "SELECT *, mpa.mpa FROM films left outer join mpa ON mpa.mpa_id = films.film_mpa";
        final List<Film> films = jdbcTemplate.query(sqlQuery, this::makeFilm);
        HashMap<Integer, Film> map = new HashMap<>();
        for (Film film : films) {
            map.put(film.getId(), film);
        }
        return map;
    }

    public boolean delete(int filmId) {
        String sqlQuery = "DELETE FROM films where film_id = ?";
        return jdbcTemplate.update(sqlQuery, filmId) > 0;
    }

    @Override
    public Film save(Film film) {
        final String sqlQuery = "INSERT INTO films(film_name, film_description, film_release_date, film_duration,film_mpa, film_rate) VALUES(?,?,?,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sqlQuery, new String[]{"film_id"});
            stmt.setString(1, film.getName());
            stmt.setString(2, film.getDescription());
            stmt.setInt(4, film.getDuration());
            stmt.setInt(5, film.getMpa().getId());
            stmt.setInt(6, film.getRate());
            if (film.getDescription() == null) {
                stmt.setNull(2, Types.VARCHAR);
            } else
                stmt.setString(2, film.getDescription());
            final LocalDate releaseDate = film.getReleaseDate();
            stmt.setDate(3, Date.valueOf(film.getReleaseDate()));
            return stmt;
        }, keyHolder);
        film.setId(Objects.requireNonNull(keyHolder.getKey()).intValue());
        return film;
    }

    @Override
    public void addLike(Film film, User user) {
        String sqlQuery = "INSERT INTO film_who_liked(film_id, user_id) VALUES(?,?)";
        jdbcTemplate.update(sqlQuery,
                film.getId(),
                user.getId());

    }

    @Override
    public void deleteLike(Film film, User user) {
        String sqlQuery = "DELETE FROM film_who_liked WHERE film_id =? and user_id = ?";
        jdbcTemplate.update(sqlQuery, film.getId(), user.getId());

    }

    @Override
    public List<Film> getMostLikeMovies(int count) {
        String sqlQuery = "SELECT *, MPA.MPA from films " +
                " left  join mpa ON mpa.mpa_id = films.film_mpa " +
                "left join FILM_WHO_LIKED on FILMS.FILM_ID = FILM_WHO_LIKED.FILM_ID " +
                "GROUP BY FILMS.FILM_ID ORDER BY(COUNT(FILM_WHO_LIKED.USER_ID)) DESC LIMIT ?";
        return jdbcTemplate.query(sqlQuery, this::makeFilm, count);
    }


}