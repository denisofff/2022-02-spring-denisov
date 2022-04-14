package ru.book.service;

import ru.book.domain.Book;

import java.util.List;

public interface BookService {
    List<Book> findBooksByName(String name);
    List<Book> findBooksByGenre(String name);
    List<Book> findBooksByAuthor(String name);

    Book getBook(int id);

    void insertBook(Book book);
    void insertBook(String name, String genresIds, String authorsIds);

    void updateBook(Book book);
    void updateBook(int id, String name, String genresIds, String authorsIds);

    void deleteBook(int id);
}
