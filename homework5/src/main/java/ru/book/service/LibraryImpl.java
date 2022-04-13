package ru.book.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.book.dao.AuthorDao;
import ru.book.dao.BookDao;
import ru.book.dao.GenreDao;
import ru.book.domain.Author;
import ru.book.domain.Book;
import ru.book.domain.Genre;

import java.util.List;

@RequiredArgsConstructor
@Service
public class LibraryImpl implements Library {
    private final GenreDao genreDao;
    private final AuthorDao authorDao;
    private final BookDao bookDao;

    @Override
    public List<Genre> findGenres(String name) {
        return name == null ? genreDao.selectAll() : genreDao.selectByName(name);
    }

    @Override
    public List<Author> findAuthors(String name) {
        return name == null ? authorDao.selectAll() : authorDao.selectByName(name);
    }

    @Override
    public List<Book> findBooksByName(String name) {
        return name == null ? bookDao.selectAll() : bookDao.selectByName(name);
    }

    @Override
    public List<Book> findBooksByGenre(String name) {
        return bookDao.selectByGenre(name);
    }

    @Override
    public List<Book> findBooksByAuthor(String name) {
        return bookDao.selectByAuthor(name);
    }

    @Override
    public Genre getGenre(int id) {
        return genreDao.get(id);
    }

    @Override
    public Author getAuthor(int id) {
        return authorDao.get(id);
    }

    @Override
    public void insertGenre(Genre genre) {
        genreDao.insert(genre);
    }

    @Override
    public void insertAuthor(Author author) {
        authorDao.insert(author);
    }

    @Override
    public void insertBook(Book book) {
        bookDao.insert(book);
    }

    @Override
    public void updateGenre(Genre genre) {
        genreDao.update(genre);
    }

    @Override
    public void updateAuthor(Author author) {
        authorDao.update(author);
    }

    @Override
    public void updateBook(Book book) {
        bookDao.update(book);
    }

    @Override
    public void deleteGenre(int id) {
        genreDao.delete(id);
    }

    @Override
    public void deleteAuthor(int id) {
        authorDao.delete(id);
    }

    @Override
    public void deleteBook(int id) {
        bookDao.delete(id);
    }
}
