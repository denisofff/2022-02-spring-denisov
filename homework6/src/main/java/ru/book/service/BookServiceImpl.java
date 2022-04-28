package ru.book.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.book.domain.Author;
import ru.book.domain.Book;
import ru.book.domain.Genre;
import ru.book.repository.AuthorRepository;
import ru.book.repository.BookRepository;
import ru.book.repository.GenreRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private final GenreRepository genreDao;
    private final AuthorRepository authorDao;
    private final BookRepository bookDao;

    @Override
    @Transactional(readOnly = true)
    public List<Book> findBooksByName(String name) {
        return name == null ? bookDao.selectAll() : bookDao.selectByName(name);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> findBooksByGenre(Genre genre) {
        return genre.getBooks();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> findBooksByAuthor(String name) {
        var authors = authorDao.selectByName(name);
        var result = new ArrayList<Book>();
        for (var author : authors) {
            result.addAll(bookDao.selectByAuthor(author));
        }
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Book getBook(int id) {
        return bookDao.get(id);
    }

    @Override
    @Transactional
    public void insertBook(Book book) {
        bookDao.insert(book);
    }

    @Override
    @Transactional
    public void insertBook(String name, String genresIds, String authorsIds) {
        bookDao.insert(
                new Book()
                        .setName(name)
                        .setGenres(genreDao.selectByIds(Arrays.stream(genresIds.split(",")).map(Integer::parseInt).toList()))
                        .setAuthors(authorDao.selectByIds(Arrays.stream(authorsIds.split(",")).map(Integer::parseInt).toList()))
        );
    }

    @Override
    @Transactional
    public void updateBook(Book book) {
        bookDao.update(book);
    }

    @Override
    @Transactional
    public void updateBook(int id, String name, String genresIds, String authorsIds) {
        bookDao.update(
                bookDao.get(id)
                        .setName(name)
                        .setGenres(genreDao.selectByIds(Arrays.stream(genresIds.split(",")).map(Integer::parseInt).toList()))
                        .setAuthors(authorDao.selectByIds(Arrays.stream(authorsIds.split(",")).map(Integer::parseInt).toList()))
        );
    }

    @Override
    @Transactional
    public void deleteBook(int id) {
        bookDao.delete(id);
    }
}
