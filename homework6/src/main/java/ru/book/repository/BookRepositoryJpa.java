package ru.book.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.book.domain.Author;
import ru.book.domain.Book;
import ru.book.domain.Genre;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BookRepositoryJpa implements BookRepository {
    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public void insert(Book book) {
        entityManager.persist(book);
    }

    @Override
    public void update(Book book) {
        entityManager.merge(book);
    }

    @Override
    public void delete(int id) {
        Query query = entityManager.createQuery("delete from Book a where a.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public Book get(int id) {
        return entityManager.find(Book.class, id);
    }

    @Override
    public List<Book> selectAll() {
        return entityManager.createQuery("select a from Book a", Book.class).getResultList();
    }

    @Override
    public List<Book> selectByName(String name) {
        TypedQuery<Book> query = entityManager.createQuery("select b from Book b where lower(name) like :name", Book.class);
        query.setParameter("name", "%" + name.toLowerCase() + "%");
        return query.getResultList();
    }

    @Override
    public List<Book> selectByGenre(Genre genre) {
        return genre.getBooks();
    }

    @Override
    public List<Book> selectByAuthor(Author author) {
        TypedQuery<Book> query = entityManager.createQuery("select b from Book b join b.authors a where lower(a.name) like :name", Book.class);
        query.setParameter("name", "%" + author.getName().toLowerCase() + "%");
        return query.getResultList();
    }
}
