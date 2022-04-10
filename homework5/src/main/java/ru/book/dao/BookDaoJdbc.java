package ru.book.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.book.domain.Author;
import ru.book.domain.Book;
import ru.book.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class BookDaoJdbc implements BookDao {

    private final static String SQL_WITHOUT_WHERE_CLAUSE = "SELECT B.Id, B.Name, G.Id AS Genre_Id, G.Name AS GenreName, A.Id AS Author_Id, A.Name AS AuthorName " +
            "FROM Books AS B " +
            "LEFT JOIN Genres AS G ON G.Id = B.Genre_Id " +
            "LEFT JOIN Authors AS A ON A.Id = B.Author_Id ";

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    @Override
    public void insert(Book book) {
        namedParameterJdbcOperations.update("INSERT INTO Books (Name, Genre_Id, Author_Id) values (:name, :genre, :author)",
                Map.of("name", book.getName(), "genre", book.getGenre().getId(), "author", book.getAuthor().getId()));
    }

    @Override
    public void update(Book book) {
        namedParameterJdbcOperations.update("UPDATE Books SET Name = :name, Genre_Id = :genre, Author_Id = :author WHERE id = :id",
                Map.of("id", book.getId(), "name", book.getName(), "genre", book.getGenre().getId(), "author", book.getAuthor().getId()));
    }

    @Override
    public Book get(int id) {
        return namedParameterJdbcOperations.queryForObject(
                SQL_WITHOUT_WHERE_CLAUSE + "WHERE B.Id = :id", Map.of("id", id), new BookMapper()
        );
    }

    @Override
    public List<Book> selectAll() {
        return namedParameterJdbcOperations.getJdbcOperations().query(SQL_WITHOUT_WHERE_CLAUSE, new BookMapper());
    }

    @Override
    public List<Book> selectByName(String name) {
        return namedParameterJdbcOperations.query(SQL_WITHOUT_WHERE_CLAUSE +
                "WHERE lower(B.Name) LIKE :name", Map.of("name", "%" + name.toLowerCase() + "%"), new BookMapper()
        );
    }

    @Override
    public List<Book> selectByGenre(String name) {
        return namedParameterJdbcOperations.query(SQL_WITHOUT_WHERE_CLAUSE +
                "WHERE lower(G.Name) LIKE :name", Map.of("name", "%" + name.toLowerCase() + "%"), new BookMapper()
        );
    }

    @Override
    public List<Book> selectByAuthor(String name) {
        return namedParameterJdbcOperations.query(SQL_WITHOUT_WHERE_CLAUSE +
                "WHERE lower(A.Name) LIKE :name", Map.of("name", "%" + name.toLowerCase() + "%"), new BookMapper()
        );
    }

    @Override
    public void delete(int id) {
        namedParameterJdbcOperations.update(
                "DELETE FROM Books WHERE Id = :id", Map.of("id", id)
        );
    }

    private static class BookMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            Genre genre = new Genre(resultSet.getInt("Genre_Id"), resultSet.getString("GenreName"));
            Author author = new Author(resultSet.getInt("Author_Id"), resultSet.getString("AuthorName"));

            return new Book(resultSet.getInt("Id"), resultSet.getString("Name"), genre, author);
        }
    }
}
