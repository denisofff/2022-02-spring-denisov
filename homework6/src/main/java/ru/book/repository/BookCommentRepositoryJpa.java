package ru.book.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.book.domain.Author;
import ru.book.domain.Book;
import ru.book.domain.BookComment;
import ru.book.service.BookService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BookCommentRepositoryJpa implements BookCommentRepository {
    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public void insert(Book book, LocalDate date, String note) {
        book.getComments().add(new BookComment().setBook(book).setDate(date).setNote(note));
        entityManager.merge(book);
    }

    @Override
    public void update(int id, Book book, LocalDate date, String note) {
        book.getComments().stream().filter(t -> t.getId().equals(id)).findFirst().orElseThrow(() -> {
            throw new RuntimeException(String.format("Comment with id = %s not found", id));
        }).setDate(date).setNote(note);
        entityManager.merge(book);
    }

    @Override
    public void delete(int id) {
        var comment = get(id);
        entityManager.remove(comment);
        var book = comment.getBook();
        book.getComments().removeIf(t -> t.getId().equals(id));
        //entityManager.merge(book);
    }

    @Override
    public BookComment get(int id) {
        return entityManager.find(BookComment.class, id);
    }
}
