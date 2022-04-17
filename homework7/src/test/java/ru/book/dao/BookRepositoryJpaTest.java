package ru.book.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.book.domain.Book;
import ru.book.repository.AuthorRepository;
import ru.book.repository.BookRepository;
import ru.book.repository.GenreRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


@DisplayName("Dao для работы с книгами")
@DataJpaTest
class BookRepositoryJpaTest {
    private static final String BOOK_NAME = "Утраченный символ";
    private static final String EXISTING_BOOK_NAME = "Ангелы и демоны";
    private static final String EXISTING_AUTHOR_NAME = "Дэн Браун";
    private static final int EXISTING_AUTHOR_ID = 1;
    private static final String EXISTING_GENRE_NAME = "Детектив";
    private static final int EXISTING_GENRE_ID = 1;
    private static final int EXISTING_BOOK_ID = 1;

    @Autowired
    private BookRepository bookDao;

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AuthorRepository authorDao;

    @Autowired
    private GenreRepository genreDao;

    @DisplayName("Добавление книги и проверка поиска по имени")
    @Test
    void shouldInsertBook() {
        Book expectedBook = new Book(null, BOOK_NAME, List.of(genreDao.getById(EXISTING_GENRE_ID)), List.of(authorDao.getById(EXISTING_AUTHOR_ID)));
        var notExistBook = bookDao.findByNameContainsIgnoreCase(BOOK_NAME);
        assertThat(notExistBook.size()).isEqualTo(0);
        bookDao.save(expectedBook);
        var foundedBook = bookDao.findByNameContainsIgnoreCase(BOOK_NAME);
        assertThat(foundedBook.get(0).getName()).isEqualTo(BOOK_NAME);
    }

    @DisplayName("Изменение книги и проверка поиска по имени")
    @Test
    void shouldUpdateBook() {
        var existingBook = bookDao.getById(EXISTING_BOOK_ID);
        assertThat(existingBook.getName()).isEqualTo(EXISTING_BOOK_NAME);
        bookDao.save(new Book(EXISTING_BOOK_ID, BOOK_NAME, List.of(genreDao.getById(EXISTING_GENRE_ID)), List.of(authorDao.getById(EXISTING_AUTHOR_ID))));
        var updatedBook = bookDao.getById(EXISTING_BOOK_ID);
        assertThat(updatedBook.getName()).isEqualTo(BOOK_NAME);
    }

    @DisplayName("Возвращать книгу по id")
    @Test
    void shouldReturnExpectedBookById() {
        var existingBook = bookDao.getById(EXISTING_BOOK_ID);
        assertThat(existingBook.getName()).isEqualTo(EXISTING_BOOK_NAME);
    }

    @DisplayName("Удаление книги по id")
    @Test
    void shouldCorrectDeleteBookById() {
        var existingBook = bookDao.getById(EXISTING_BOOK_ID);

        bookDao.delete(existingBook);
        entityManager.flush();

        assertThatThrownBy(() -> {
            var foundedBook = bookDao.getById(EXISTING_BOOK_ID);
            System.out.println(foundedBook);
        }).isInstanceOf(EntityNotFoundException.class);
    }

    @DisplayName("Проверка ожидаемого списка книг")
    @Test
    void shouldReturnExpectedBooksList() {
        Book expectedBook = new Book(EXISTING_BOOK_ID, EXISTING_BOOK_NAME, List.of(genreDao.getById(EXISTING_GENRE_ID)), List.of(authorDao.getById(EXISTING_AUTHOR_ID)));
        List<Book> actualBookList = bookDao.findAll();
        assertThat(actualBookList).containsAnyOf(expectedBook);
    }

    @DisplayName("Проверка поиска по автору")
    @Test
    void shouldReturnExpectedBooksListByAuthor() {
        Book expectedBook = new Book(EXISTING_BOOK_ID, EXISTING_BOOK_NAME, List.of(genreDao.getById(EXISTING_GENRE_ID)), List.of(authorDao.getById(EXISTING_AUTHOR_ID)));
        List<Book> actualBookList = bookDao.findByAuthor(EXISTING_AUTHOR_NAME);
        assertThat(actualBookList).containsAnyOf(expectedBook);
    }

    @DisplayName("Проверка поиска по жанру")
    @Test
    void shouldReturnExpectedBooksListByGenre() {
        Book expectedBook = new Book(EXISTING_BOOK_ID, EXISTING_BOOK_NAME, List.of(genreDao.getById(EXISTING_GENRE_ID)), List.of(authorDao.getById(EXISTING_AUTHOR_ID)));
        List<Book> actualBookList = bookDao.findByGenre(EXISTING_GENRE_NAME);
        assertThat(actualBookList).containsAnyOf(expectedBook);
    }
}