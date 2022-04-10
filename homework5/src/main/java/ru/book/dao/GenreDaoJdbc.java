package ru.book.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.book.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class GenreDaoJdbc implements GenreDao {

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    @Override
    public void insert(Genre genre) {
        namedParameterJdbcOperations.update("INSERT INTO Genres (name) values (:name)",
                Map.of("name", genre.getName()));
    }

    @Override
    public void update(Genre genre) {
        namedParameterJdbcOperations.update("UPDATE Genres SET name = :name WHERE id = :id",
                Map.of("id", genre.getId(), "name", genre.getName()));
    }

    @Override
    public Genre get(int id) {
        return namedParameterJdbcOperations.queryForObject(
                "SELECT Id, Name FROM Genres WHERE Id = :id", Map.of("id", id), new GenreMapper()
        );
    }

    @Override
    public List<Genre> selectAll() {
        return namedParameterJdbcOperations.getJdbcOperations().query("SELECT Id, Name FROM Genres", new GenreMapper());
    }

    @Override
    public List<Genre> selectByName(String name) {
        return namedParameterJdbcOperations.query(
                "SELECT Id, Name FROM Genres WHERE lower(Name) LIKE :name", Map.of("name", "%" + name.toLowerCase() + "%"), new GenreMapper()
        );
    }

    @Override
    public void delete(int id) {
        namedParameterJdbcOperations.update(
                "DELETE FROM Genres WHERE Id = :id", Map.of("id", id)
        );
    }

    private static class GenreMapper implements RowMapper<Genre> {

        @Override
        public Genre mapRow(ResultSet resultSet, int i) throws SQLException {
            int id = resultSet.getInt("Id");
            String name = resultSet.getString("Name");
            return new Genre(id, name);
        }
    }
}
