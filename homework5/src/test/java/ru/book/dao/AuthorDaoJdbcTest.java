package ru.book.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.book.domain.Author;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Dao для работы с авторами")
@JdbcTest
@Import(AuthorDaoJdbc.class)
class AuthorDaoJdbcTest {
    private static final String AUTHOR_NAME = "Агата Кристи";
    private static final String EXISTING_AUTHOR_NAME = "Владимир Васильев";
    private static final int EXISTING_AUTHOR_ID = 4;
    
    @Autowired
    private AuthorDaoJdbc authorDao;

    @DisplayName("Добавление автора и проверка поиска по имени")
    @Test
    void shouldInsertAuthor() {
        Author expectedAuthor = new Author(null, AUTHOR_NAME);
        var notExistAuthor = authorDao.selectByName(AUTHOR_NAME);
        assertThat(notExistAuthor.size()).isEqualTo(0);
        authorDao.insert(expectedAuthor);
        var foundedAuthor = authorDao.selectByName(AUTHOR_NAME);
        assertThat(foundedAuthor.get(0).getName()).isEqualTo(AUTHOR_NAME);
    }

    @DisplayName("Изменение автора и проверка поиска по имени")
    @Test
    void shouldUpdateAuthor() {
        var existingAuthor = authorDao.get(EXISTING_AUTHOR_ID);
        assertThat(existingAuthor.getName()).isEqualTo(EXISTING_AUTHOR_NAME);
        authorDao.update(new Author(EXISTING_AUTHOR_ID, AUTHOR_NAME));
        var updatedAuthor = authorDao.get(EXISTING_AUTHOR_ID);
        assertThat(updatedAuthor.getName()).isEqualTo(AUTHOR_NAME);
    }

    @DisplayName("Возвращать автора по id")
    @Test
    void shouldReturnExpectedAuthorById() {
        var existingAuthor = authorDao.get(EXISTING_AUTHOR_ID);
        assertThat(existingAuthor.getName()).isEqualTo(EXISTING_AUTHOR_NAME);
    }

    @DisplayName("Удаление автора по id")
    @Test
    void shouldCorrectDeleteAuthorById() {
        assertThatCode(() -> authorDao.get(EXISTING_AUTHOR_ID))
                .doesNotThrowAnyException();

        authorDao.delete(EXISTING_AUTHOR_ID);

        assertThatThrownBy(() -> authorDao.get(EXISTING_AUTHOR_ID))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }

    @DisplayName("Проверка ожидаемого списка авторов")
    @Test
    void shouldReturnExpectedAuthorsList() {
        Author expectedAuthor = new Author(EXISTING_AUTHOR_ID, EXISTING_AUTHOR_NAME);
        List<Author> actualAuthorList = authorDao.selectAll();
        assertThat(actualAuthorList).contains(expectedAuthor);
    }
}