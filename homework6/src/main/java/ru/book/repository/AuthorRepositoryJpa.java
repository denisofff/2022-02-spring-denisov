package ru.book.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.book.domain.Author;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.Arrays;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class AuthorRepositoryJpa implements AuthorRepository {
    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public void insert(Author author) {
        entityManager.persist(author);
    }

    @Override
    public void update(Author author) {
        entityManager.merge(author);
    }

    @Override
    public void delete(int id) {
        Query query = entityManager.createQuery("delete from Author a where a.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public Author get(int id) {
        return entityManager.find(Author.class, id);
    }

    @Override
    public List<Author> selectAll() {
        return entityManager.createQuery("select a from Author a", Author.class).getResultList();
    }

    @Override
    public List<Author> selectByIds(String ids) {
        TypedQuery<Author> query = entityManager.createQuery("select a from Author a where a.id IN :ids", Author.class);
        query.setParameter("ids", Arrays.stream(ids.split(",")).map(Integer::parseInt).toList());
        return query.getResultList();
    }

    @Override
    public List<Author> selectByName(String name) {
        TypedQuery<Author> query = entityManager.createQuery("select a from Author a where lower(a.name) like :name", Author.class);
        query.setParameter("name", "%" + name.toLowerCase() + "%");
        return query.getResultList();
    }
}
