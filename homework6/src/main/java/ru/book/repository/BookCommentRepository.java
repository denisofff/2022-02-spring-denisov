package ru.book.repository;

import ru.book.domain.Book;
import ru.book.domain.BookComment;

import java.util.List;

public interface BookCommentRepository {
    void insert(BookComment bookComment);
    void update(BookComment bookComment);
    void delete(int id);

    BookComment get(int id);

    List<BookComment> selectByBook(Book book);
}
