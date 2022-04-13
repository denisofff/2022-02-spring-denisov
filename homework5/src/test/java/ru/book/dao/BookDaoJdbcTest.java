package ru.book.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.book.domain.Author;
import ru.book.domain.Book;
import ru.book.domain.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Dao для работы с книгами")
@JdbcTest
@Import(BookDaoJdbc.class)
class BookDaoJdbcTest {
    private static final String BOOK_NAME = "Утраченный символ";
    private static final String EXISTING_BOOK_NAME = "Ангелы и демоны";
    private static final String EXISTING_AUTHOR_NAME = "Дэн Браун";
    private static final int EXISTING_AUTHOR_ID = 1;
    private static final String EXISTING_GENRE_NAME = "Детектив";
    private static final int EXISTING_GENRE_ID = 1;
    private static final int EXISTING_BOOK_ID = 1;
    
    @Autowired
    private BookDaoJdbc bookDao;

    @MockBean
    private AuthorDaoJdbc authorDao;

    @MockBean
    private GenreDao genreDao;

    @BeforeEach
    void mockData() {
        Mockito.when(authorDao.get(EXISTING_AUTHOR_ID)).thenReturn(new Author(EXISTING_AUTHOR_ID, EXISTING_AUTHOR_NAME));
        Mockito.when(genreDao.get(EXISTING_GENRE_ID)).thenReturn(new Genre(EXISTING_GENRE_ID, EXISTING_GENRE_NAME));
    }

    @DisplayName("Добавление книги и проверка поиска по имени")
    @Test
    void shouldInsertBook() {
        Book expectedBook = new Book(null, BOOK_NAME, genreDao.get(EXISTING_GENRE_ID), authorDao.get(EXISTING_AUTHOR_ID));
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
        bookDao.update(new Book(EXISTING_BOOK_ID, BOOK_NAME, genreDao.get(EXISTING_GENRE_ID), authorDao.get(EXISTING_AUTHOR_ID)));
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
        assertThatCode(() -> bookDao.get(EXISTING_BOOK_ID))
                .doesNotThrowAnyException();

        bookDao.delete(EXISTING_BOOK_ID);

        assertThatThrownBy(() -> bookDao.get(EXISTING_BOOK_ID))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }

    @DisplayName("Проверка ожидаемого списка книг")
    @Test
    void shouldReturnExpectedBooksList() {
        Book expectedBook = new Book(EXISTING_BOOK_ID, EXISTING_BOOK_NAME, genreDao.get(EXISTING_GENRE_ID), authorDao.get(EXISTING_AUTHOR_ID));
        List<Book> actualBookList = bookDao.selectAll();
        assertThat(actualBookList).contains(expectedBook);
    }

    @DisplayName("Проверка поиска по автору")
    @Test
    void shouldReturnExpectedBooksListByAuthor() {
        Book expectedBook = new Book(EXISTING_BOOK_ID, EXISTING_BOOK_NAME, genreDao.get(EXISTING_GENRE_ID), authorDao.get(EXISTING_AUTHOR_ID));
        List<Book> actualBookList = bookDao.selectByAuthor(EXISTING_AUTHOR_NAME);
        assertThat(actualBookList).contains(expectedBook);
    }

    @DisplayName("Проверка поиска по жанру")
    @Test
    void shouldReturnExpectedBooksListByGenre() {
        Book expectedBook = new Book(EXISTING_BOOK_ID, EXISTING_BOOK_NAME, genreDao.get(EXISTING_GENRE_ID), authorDao.get(EXISTING_AUTHOR_ID));
        List<Book> actualBookList = bookDao.selectByGenre(EXISTING_GENRE_NAME);
        assertThat(actualBookList).contains(expectedBook);
    }
}