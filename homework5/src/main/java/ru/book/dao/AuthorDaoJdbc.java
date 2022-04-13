package ru.book.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.book.domain.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class AuthorDaoJdbc implements AuthorDao {

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    @Override
    public void insert(Author author) {
        namedParameterJdbcOperations.update("INSERT INTO Authors (name) values (:name)",
                Map.of("name", author.getName()));
    }

    @Override
    public void update(Author author) {
        namedParameterJdbcOperations.update("UPDATE Authors SET Name = :name WHERE Id = :id",
                Map.of("name", author.getName(), "id", author.getId()));
    }

    @Override
    public Author get(int id) {
        return namedParameterJdbcOperations.queryForObject(
                "SELECT Id, Name FROM Authors WHERE Id = :id", Map.of("id", id), new AuthorMapper()
        );
    }

    @Override
    public List<Author> selectAll() {
        return namedParameterJdbcOperations.getJdbcOperations().query("SELECT Id, Name FROM Authors", new AuthorMapper());
    }

    @Override
    public List<Author> selectByName(String name) {
        return namedParameterJdbcOperations.query(
                "SELECT Id, Name FROM Authors WHERE lower(Name) LIKE :name", Map.of("name", "%" + name.toLowerCase() + "%"), new AuthorMapper()
        );
    }

    @Override
    public void delete(int id) {
        namedParameterJdbcOperations.update(
                "DELETE FROM Authors WHERE Id = :id", Map.of("id", id)
        );
    }

    private static class AuthorMapper implements RowMapper<Author> {

        @Override
        public Author mapRow(ResultSet resultSet, int i) throws SQLException {
            int id = resultSet.getInt("Id");
            String name = resultSet.getString("Name");
            return new Author(id, name);
        }
    }
}
