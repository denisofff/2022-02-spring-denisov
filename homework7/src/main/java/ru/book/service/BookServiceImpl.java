package ru.book.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.book.domain.Book;
import ru.book.repository.AuthorRepository;
import ru.book.repository.BookRepository;
import ru.book.repository.GenreRepository;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private final GenreRepository genreDao;
    private final AuthorRepository authorDao;
    private final BookRepository bookDao;

    @Override
    public List<Book> findBooksByName(String name) {
        return name == null ? bookDao.findAll() : bookDao.findByNameContainsIgnoreCase(name);
    }

    @Override
    public List<Book> findBooksByGenre(String name) {
        return bookDao.findByGenre(name);
    }

    @Override
    public List<Book> findBooksByAuthor(String name) {
        return bookDao.findByAuthor(name);
    }

    @Override
    public Book getBook(int id) {
        return bookDao.findById(id).orElseThrow();
    }

    @Override
    @Transactional
    public void insertBook(Book book) {
        bookDao.save(book);
    }

    @Override
    @Transactional
    public void insertBook(String name, String genresIds, String authorsIds) {
        var authorsIdsList = Arrays.stream(authorsIds.split(",")).map(Integer::parseInt).toList();
        var genresIdsList = Arrays.stream(genresIds.split(",")).map(Integer::parseInt).toList();

        bookDao.save(new Book(null, name, genreDao.findByIds(genresIdsList), authorDao.findByIds(authorsIdsList)));
    }

    @Override
    @Transactional
    public void updateBook(Book book) {
        bookDao.save(book);
    }

    @Override
    @Transactional
    public void updateBook(int id, String name, String genresIds, String authorsIds) {
        var authorsIdsList = Arrays.stream(authorsIds.split(",")).map(Integer::parseInt).toList();
        var genresIdsList = Arrays.stream(genresIds.split(",")).map(Integer::parseInt).toList();

        bookDao.save(new Book(id, name, genreDao.findByIds(genresIdsList), authorDao.findByIds(authorsIdsList)));
    }

    @Override
    @Transactional
    public void deleteBook(int id) {
        bookDao.delete(getBook(id));
    }
}
