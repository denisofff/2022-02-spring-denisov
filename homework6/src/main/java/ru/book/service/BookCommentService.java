package ru.book.service;

import ru.book.domain.Book;
import ru.book.domain.BookComment;

import java.util.List;

public interface BookCommentService {
    List<BookComment> findByBook(Book book);

    BookComment getBookComment(int id);

    void insertBookComment(BookComment bookComment);

    void updateBookComment(BookComment bookComment);

    void deleteBookComment(int id);
}
