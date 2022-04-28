package ru.book.repository;

import ru.book.domain.Book;
import ru.book.domain.BookComment;

import java.time.LocalDate;
import java.util.List;

public interface BookCommentRepository {
    void insert(Book book, LocalDate date, String note);
    void update(int id, Book book, LocalDate date, String note);
    void delete(int id);

    BookComment get(int id);
}
