package ru.book.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.book.domain.BookComment;
import ru.book.repository.BookCommentRepository;
import ru.book.repository.BookRepository;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("Dao для работы с комментариями")
@DataJpaTest
class BookCommentRepositoryJpaTest {
    private static final int EXISTING_BOOK_ID = 1;
    private static final String COMMENT_TEXT = "TEST COMMENT TEXT";
    private static final String MODIFIED_COMMENT_TEXT = "MODIFIED COMMENT TEXT";

    @Autowired
    BookCommentRepository bookCommentDao;

    @Autowired
    private BookRepository bookDao;

    @Autowired
    private TestEntityManager entityManager;

    @DisplayName("Добавление комментария и проверка того, что он появился")
    @Test
    void shouldInsertBookComment() {
        bookCommentDao.save(new BookComment(null, bookDao.getById(EXISTING_BOOK_ID), LocalDate.now(), COMMENT_TEXT));

        var comments = bookCommentDao.findByBook(bookDao.getById(EXISTING_BOOK_ID));
        assertThat(comments.size()).isEqualTo(1);
        assertThat(comments.get(0).getNote()).isEqualTo(COMMENT_TEXT);
    }

    @DisplayName("Изменение комментария")
    @Test
    void shouldUpdateBookComment() {
        bookCommentDao.save(new BookComment(null, bookDao.getById(EXISTING_BOOK_ID), LocalDate.now(), COMMENT_TEXT));

        var comments = bookCommentDao.findByBook(bookDao.getById(EXISTING_BOOK_ID));

        assertThat(comments.size()).isEqualTo(1);
        assertThat(comments.get(0).getNote()).isEqualTo(COMMENT_TEXT);

        var comment = comments.get(0);
        comment.setNote(MODIFIED_COMMENT_TEXT);
        bookCommentDao.save(comment);

        comments = bookCommentDao.findByBook(bookDao.getById(EXISTING_BOOK_ID));

        assertThat(comments.size()).isEqualTo(1);
        assertThat(comments.get(0).getNote()).isEqualTo(MODIFIED_COMMENT_TEXT);
    }

    @DisplayName("Возвращать комментарий по id")
    @Test
    void shouldReturnExpectedBookCommentById() {
        bookCommentDao.save(new BookComment(null, bookDao.getById(EXISTING_BOOK_ID), LocalDate.now(), COMMENT_TEXT));

        var comments = bookCommentDao.findByBook(bookDao.getById(EXISTING_BOOK_ID));

        assertThat(comments.size()).isEqualTo(1);
        assertThat(comments.get(0).getNote()).isEqualTo(COMMENT_TEXT);

        var comment = comments.get(0);

        var commentFromDao = bookCommentDao.getById(comment.getId());
        assertThat(comment).isEqualTo(commentFromDao);
    }

    @DisplayName("Удаление комментария по id")
    @Test
    void shouldCorrectDeleteBookCommentById() {
        bookCommentDao.save(new BookComment(null, bookDao.getById(EXISTING_BOOK_ID), LocalDate.now(), COMMENT_TEXT));

        var comments = bookCommentDao.findByBook(bookDao.getById(EXISTING_BOOK_ID));

        assertThat(comments.size()).isEqualTo(1);
        assertThat(comments.get(0).getNote()).isEqualTo(COMMENT_TEXT);

        var comment = comments.get(0);

        bookCommentDao.delete(comment);

        comments = bookCommentDao.findByBook(bookDao.getById(EXISTING_BOOK_ID));
        assertThat(comments.size()).isEqualTo(0);
    }
}