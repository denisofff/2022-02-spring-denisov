package ru.book.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.book.domain.Author;
import ru.book.repository.AuthorRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Dao для работы с авторами")
@DataJpaTest
class AuthorRepositoryJpaTest {
    private static final String AUTHOR_NAME = "Василий Пупкин";
    private static final String EXISTING_AUTHOR_NAME = "Агата Кристи";
    private static final int EXISTING_AUTHOR_ID = 5;

    @Autowired
    private AuthorRepository authorDao;

    @Autowired
    TestEntityManager testEntityManager;

    @DisplayName("Добавление автора и проверка поиска по имени")
    @Test
    void shouldInsertAuthor() {
        Author expectedAuthor = new Author(null, AUTHOR_NAME);
        var notExistAuthor = authorDao.findByNameContainsIgnoreCase(AUTHOR_NAME);
        assertThat(notExistAuthor.size()).isEqualTo(0);
        authorDao.save(expectedAuthor);
        var foundedAuthor = authorDao.findByNameContainsIgnoreCase(AUTHOR_NAME);
        assertThat(foundedAuthor.get(0).getName()).isEqualTo(AUTHOR_NAME);
    }

    @DisplayName("Изменение автора и проверка поиска по имени")
    @Test
    void shouldUpdateAuthor() {
        var existingAuthor = authorDao.getById(EXISTING_AUTHOR_ID);
        assertThat(existingAuthor.getName()).isEqualTo(EXISTING_AUTHOR_NAME);
        authorDao.save(new Author(EXISTING_AUTHOR_ID, AUTHOR_NAME));
        var updatedAuthor = authorDao.getById(EXISTING_AUTHOR_ID);
        assertThat(updatedAuthor.getName()).isEqualTo(AUTHOR_NAME);
    }

    @DisplayName("Возвращать автора по id")
    @Test
    void shouldReturnExpectedAuthorById() {
        var existingAuthor = authorDao.getById(EXISTING_AUTHOR_ID);
        assertThat(existingAuthor.getName()).isEqualTo(EXISTING_AUTHOR_NAME);
    }

    @DisplayName("Удаление автора по id")
    @Test
    void shouldCorrectDeleteAuthorById() {
        var existingAuthor = authorDao.getById(EXISTING_AUTHOR_ID);

        authorDao.delete(existingAuthor);

        testEntityManager.flush();

        assertThatThrownBy(() -> {
            var foundedAuthor = authorDao.getById(EXISTING_AUTHOR_ID);
            System.out.println(foundedAuthor);
        }).isInstanceOf(EntityNotFoundException.class);
    }

    @DisplayName("Проверка ожидаемого списка авторов")
    @Test
    void shouldReturnExpectedAuthorsList() {
        Author expectedAuthor = new Author(EXISTING_AUTHOR_ID, EXISTING_AUTHOR_NAME);
        List<Author> actualAuthorList = authorDao.findAll();
        assertThat(actualAuthorList).contains(expectedAuthor);
    }
}