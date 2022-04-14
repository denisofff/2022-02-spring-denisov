package ru.book.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.book.domain.Book;
import ru.book.repository.AuthorRepositoryJpa;
import ru.book.repository.BookRepositoryJpa;
import ru.book.repository.GenreRepositoryJpa;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("Dao для работы с книгами")
@DataJpaTest
@Import({BookRepositoryJpa.class, AuthorRepositoryJpa.class, GenreRepositoryJpa.class})
class BookRepositoryJpaTest {
    private static final String BOOK_NAME = "Утраченный символ";
    private static final String EXISTING_BOOK_NAME = "Ангелы и демоны";
    private static final String EXISTING_AUTHOR_NAME = "Дэн Браун";
    private static final int EXISTING_AUTHOR_ID = 1;
    private static final String EXISTING_GENRE_NAME = "Детектив";
    private static final int EXISTING_GENRE_ID = 1;
    private static final int EXISTING_BOOK_ID = 1;

    @Autowired
    private BookRepositoryJpa bookDao;

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AuthorRepositoryJpa authorDao;

    @Autowired
    private GenreRepositoryJpa genreDao;

    @DisplayName("Добавление книги и проверка поиска по имени")
    @Test
    void shouldInsertBook() {
        Book expectedBook = new Book(null, BOOK_NAME, List.of(genreDao.get(EXISTING_GENRE_ID)), List.of(authorDao.get(EXISTING_AUTHOR_ID)));
        var notExistBook = bookDao.selectByName(BOOK_NAME);
        assertThat(notExistBook.size()).isEqualTo(0);
        bookDao.insert(expectedBook);
        var foundedBook = bookDao.selectByName(BOOK_NAME);
        assertThat(foundedBook.get(0).getName()).isEqualTo(BOOK_NAME);
    }

    @DisplayName("Изменение книги и проверка поиска по имени")
    @Test
    void shouldUpdateBook() {
        var existingBook = bookDao.get(EXISTING_BOOK_ID);
        assertThat(existingBook.getName()).isEqualTo(EXISTING_BOOK_NAME);
        bookDao.update(new Book(EXISTING_BOOK_ID, BOOK_NAME, List.of(genreDao.get(EXISTING_GENRE_ID)), List.of(authorDao.get(EXISTING_AUTHOR_ID))));
        var updatedBook = bookDao.get(EXISTING_BOOK_ID);
        assertThat(updatedBook.getName()).isEqualTo(BOOK_NAME);
    }

    @DisplayName("Возвращать книгу по id")
    @Test
    void shouldReturnExpectedBookById() {
        var existingBook = bookDao.get(EXISTING_BOOK_ID);
        assertThat(existingBook.getName()).isEqualTo(EXISTING_BOOK_NAME);
    }

    @DisplayName("Удаление книги по id")
    @Test
    void shouldCorrectDeleteBookById() {
        var existingBook = bookDao.get(EXISTING_BOOK_ID);

        entityManager.detach(existingBook);
        bookDao.delete(EXISTING_BOOK_ID);

        assertThat(bookDao.get(EXISTING_BOOK_ID)).isEqualTo(null);
    }

    @DisplayName("Проверка ожидаемого списка книг")
    @Test
    void shouldReturnExpectedBooksList() {
        Book expectedBook = new Book(EXISTING_BOOK_ID, EXISTING_BOOK_NAME, List.of(genreDao.get(EXISTING_GENRE_ID)), List.of(authorDao.get(EXISTING_AUTHOR_ID)));
        List<Book> actualBookList = bookDao.selectAll();
        assertThat(actualBookList).containsAnyOf(expectedBook);
    }

    @DisplayName("Проверка поиска по автору")
    @Test
    void shouldReturnExpectedBooksListByAuthor() {
        Book expectedBook = new Book(EXISTING_BOOK_ID, EXISTING_BOOK_NAME, List.of(genreDao.get(EXISTING_GENRE_ID)), List.of(authorDao.get(EXISTING_AUTHOR_ID)));
        List<Book> actualBookList = bookDao.selectByAuthor(EXISTING_AUTHOR_NAME);
        assertThat(actualBookList).containsAnyOf(expectedBook);
    }

    @DisplayName("Проверка поиска по жанру")
    @Test
    void shouldReturnExpectedBooksListByGenre() {
        Book expectedBook = new Book(EXISTING_BOOK_ID, EXISTING_BOOK_NAME, List.of(genreDao.get(EXISTING_GENRE_ID)), List.of(authorDao.get(EXISTING_AUTHOR_ID)));
        List<Book> actualBookList = bookDao.selectByGenre(EXISTING_GENRE_NAME);
        assertThat(actualBookList).containsAnyOf(expectedBook);
    }
}