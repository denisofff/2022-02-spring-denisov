package ru.book.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.book.domain.BookComment;
import ru.book.repository.AuthorRepositoryJpa;
import ru.book.repository.BookCommentRepositoryJpa;
import ru.book.repository.BookRepositoryJpa;
import ru.book.repository.GenreRepositoryJpa;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("Dao для работы с комментариями")
@DataJpaTest
@Import({BookCommentRepositoryJpa.class, BookRepositoryJpa.class, AuthorRepositoryJpa.class, GenreRepositoryJpa.class})
class BookCommentRepositoryJpaTest {
    private static final int EXISTING_BOOK_ID = 1;
    private static final String COMMENT_TEXT = "TEST COMMENT TEXT";
    private static final String MODIFIED_COMMENT_TEXT = "MODIFIED COMMENT TEXT";

    @Autowired BookCommentRepositoryJpa bookCommentDao;

    @Autowired
    private BookRepositoryJpa bookDao;

    @Autowired
    private TestEntityManager entityManager;

    @DisplayName("Добавление комментария и проверка того, что он появился")
    @Test
    void shouldInsertBookComment() {
        var book = bookDao.get(EXISTING_BOOK_ID);
        bookCommentDao.insert(book, LocalDate.now(), COMMENT_TEXT);

        entityManager.detach(book);

        var comments = book.getComments();
        assertThat(comments.size()).isEqualTo(1);
        assertThat(comments.get(0).getNote()).isEqualTo(COMMENT_TEXT);
    }

    @DisplayName("Изменение комментария")
    @Test
    void shouldUpdateBookComment() {
        bookCommentDao.insert(bookDao.get(EXISTING_BOOK_ID), LocalDate.now(), COMMENT_TEXT);

        var comments = bookDao.get(EXISTING_BOOK_ID).getComments();

        assertThat(comments.size()).isEqualTo(1);
        assertThat(comments.get(0).getNote()).isEqualTo(COMMENT_TEXT);

        var comment = comments.get(0);
        bookCommentDao.update(comment.getId(), bookDao.get(EXISTING_BOOK_ID), comment.getDate(), MODIFIED_COMMENT_TEXT);

        comments = bookDao.get(EXISTING_BOOK_ID).getComments();

        assertThat(comments.size()).isEqualTo(1);
        assertThat(comments.get(0).getNote()).isEqualTo(MODIFIED_COMMENT_TEXT);
    }

    @DisplayName("Возвращать комментарий по id")
    @Test
    void shouldReturnExpectedBookCommentById() {
        bookCommentDao.insert(bookDao.get(EXISTING_BOOK_ID), LocalDate.now(), COMMENT_TEXT);

        var comments = bookDao.get(EXISTING_BOOK_ID).getComments();

        assertThat(comments.size()).isEqualTo(1);
        assertThat(comments.get(0).getNote()).isEqualTo(COMMENT_TEXT);

        var comment = comments.get(0);

        var commentFromDao = bookCommentDao.get(comment.getId());
        assertThat(comment).isEqualTo(commentFromDao);
    }

    @DisplayName("Удаление комментария по id")
    @Test
    void shouldCorrectDeleteBookCommentById() {
        bookCommentDao.insert(bookDao.get(EXISTING_BOOK_ID), LocalDate.now(), COMMENT_TEXT);

        var comments = bookDao.get(EXISTING_BOOK_ID).getComments();

        assertThat(comments.size()).isEqualTo(1);
        assertThat(comments.get(0).getNote()).isEqualTo(COMMENT_TEXT);

        var comment = comments.get(0);


        bookCommentDao.delete(comment.getId());

        comments = bookDao.get(EXISTING_BOOK_ID).getComments();
        assertThat(comments.size()).isEqualTo(0);
    }
}