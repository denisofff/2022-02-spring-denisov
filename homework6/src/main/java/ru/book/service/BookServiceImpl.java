package ru.book.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.book.domain.Book;
import ru.book.repository.AuthorRepository;
import ru.book.repository.BookRepository;
import ru.book.repository.GenreRepository;

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
    public List<Book> findBooksByGenre(String name) {
        return bookDao.selectByGenre(name);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> findBooksByAuthor(String name) {
        return bookDao.selectByAuthor(name);
    }

    @Override
    @Transactional(readOnly = false)
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
        bookDao.insert(new Book(null, name, genreDao.selectByIds(genresIds), authorDao.selectByIds(authorsIds)));
    }

    @Override
    @Transactional
    public void updateBook(Book book) {
        bookDao.update(book);
    }

    @Override
    @Transactional
    public void updateBook(int id, String name, String genresIds, String authorsIds) {
        bookDao.update(new Book(id, name, genreDao.selectByIds(genresIds), authorDao.selectByIds(authorsIds)));
    }

    @Override
    @Transactional
    public void deleteBook(int id) {
        bookDao.delete(id);
    }
}
