package ru.book.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.book.domain.Author;
import ru.book.domain.Book;
import ru.book.domain.BookComment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.Arrays;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BookCommentRepositoryJpa implements BookCommentRepository {
    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public void insert(BookComment bookComment) {
        entityManager.persist(bookComment);
    }

    @Override
    public void update(BookComment bookComment) {
        entityManager.merge(bookComment);
    }

    @Override
    public void delete(int id) {
        Query query = entityManager.createQuery("delete from BookComment a where a.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public BookComment get(int id) {
        return entityManager.find(BookComment.class, id);
    }

    @Override
    public List<BookComment> selectByBook(Book book) {
        TypedQuery<BookComment> query = entityManager.createQuery("select c from BookComment c join c.book b where b.id = :id", BookComment.class);
        query.setParameter("id", book.getId());
        return query.getResultList();
    }
}
