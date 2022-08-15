package ru.yandex.practicum.filmorate.storage.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.models.Film;
import ru.yandex.practicum.filmorate.models.Genre;
import ru.yandex.practicum.filmorate.storage.GenreStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Repository
public class GenreDbStorage implements GenreStorage {
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public GenreDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private Genre makeGenre(ResultSet resultSet, int rowNum) throws SQLException {
        return new Genre(
                resultSet.getInt("genres.genre_id"), resultSet.getString("genres.GENRE"));
    }

    @Override
    public Set<Genre> getGenres() {
        String sqlQuery = "SELECT * FROM genres ORDER BY genre_id";
        return new LinkedHashSet<>(jdbcTemplate.query(sqlQuery, this::makeGenre));
    }

    @Override
    public Genre getGenresById(int id) {
        String sqlQuery = "SELECT genres.genre_id, genres.genre from genres where genres.genre_id = ?";
        final List<Genre> genres = new ArrayList<>(jdbcTemplate.query(sqlQuery, this::makeGenre, id));

        return genres.get(0);
    }

    @Override
    public void setGenres(Film film) {
        final String sqlQuery2 = "INSERT INTO genres_of_film(genre_id, film_id) VALUES (?,?)";
        if (film.getGenres() != null) {
            for (Genre genre : film.getGenres()) {
                jdbcTemplate.update(sqlQuery2,
                        genre.getId(),
                        film.getId());

            }
        }

    }

    @Override
    public void deleteGenres(Film film) {
        String sqlQuery = "DELETE FROM genres_of_film WHERE film_id = ?";
        jdbcTemplate.update(sqlQuery, film.getId());
    }

    @Override
    public void loadFilmGenre(Film film) {
        final String sqlQuery = "SELECT genres_of_film.genre_id, genres.genre, genres.genre_id from genres_of_film  left outer join genres   ON genres.genre_id = genres_of_film.genre_id where genres_of_film.film_id = ?";
        final Set<Genre> genres = new LinkedHashSet<>(jdbcTemplate.query(sqlQuery, this::makeGenre, film.getId()));
        if (genres.size() != 0)
            for (Genre genre : genres)
                film.getGenres().add(genre);

    }
}

