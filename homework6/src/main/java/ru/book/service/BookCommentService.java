package ru.book.service;

import ru.book.domain.Book;
import ru.book.domain.BookComment;

import java.time.LocalDate;
import java.util.List;

public interface BookCommentService {
    List<BookComment> findByBook(Book book);

    BookComment getBookComment(int id);

    void insertBookComment(Book book, LocalDate date, String note);

    void updateBookComment(int id, Book book, LocalDate date, String note);

    void deleteBookComment(int id);
}
