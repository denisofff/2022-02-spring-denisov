package ru.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.book.domain.Book;
import ru.book.domain.BookComment;

import java.util.List;

public interface BookCommentRepository extends JpaRepository<BookComment, Integer> {
    List<BookComment> findByBook(Book book);
}
