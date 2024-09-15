package ru.yandex.practicum.filmorate.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.RatingStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class RatingDbStorage implements RatingStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Mpa> findAllRatings() {
        String sql = "SELECT * FROM mpa_rating";
        return jdbcTemplate.query(sql, (rs, rowNum) -> makeRating(rs));
    }

    @Override
    public Optional<Mpa> findRatingById(int id) {
        String sql = "SELECT * FROM mpa_rating WHERE rating_id =?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> makeRating(rs), id).stream().findFirst();
    }

    private Mpa makeRating(ResultSet rs) throws SQLException {
        int id = rs.getInt("rating_id");
        String name = rs.getString("name");
        String description = rs.getString("description");
        return new Mpa(id, name, description);
    }
}
