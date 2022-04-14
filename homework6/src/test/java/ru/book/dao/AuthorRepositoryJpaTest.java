package ru.book.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.book.domain.Author;
import ru.book.repository.AuthorRepositoryJpa;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Dao для работы с авторами")
@DataJpaTest
@Import(AuthorRepositoryJpa.class)
class AuthorRepositoryJpaTest {
    private static final String AUTHOR_NAME = "Василий Пупкин";
    private static final String EXISTING_AUTHOR_NAME = "Агата Кристи";
    private static final int EXISTING_AUTHOR_ID = 5;

    @Autowired
    private AuthorRepositoryJpa authorDao;

    @Autowired
    private TestEntityManager entityManager;

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
        var existingAuthor = authorDao.get(EXISTING_AUTHOR_ID);

        entityManager.detach(existingAuthor);
        authorDao.delete(EXISTING_AUTHOR_ID);

        assertThat(authorDao.get(EXISTING_AUTHOR_ID)).isEqualTo(null);
    }

    @DisplayName("Проверка ожидаемого списка авторов")
    @Test
    void shouldReturnExpectedAuthorsList() {
        Author expectedAuthor = new Author(EXISTING_AUTHOR_ID, EXISTING_AUTHOR_NAME);
        List<Author> actualAuthorList = authorDao.selectAll();
        assertThat(actualAuthorList).contains(expectedAuthor);
    }
}