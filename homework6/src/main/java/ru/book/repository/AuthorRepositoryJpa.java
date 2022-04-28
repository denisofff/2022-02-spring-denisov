package ru.book.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.book.domain.Author;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.Arrays;
import java.util.List;

@Component
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
        entityManager.remove(get(id));
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
    public List<Author> selectByIds(List<Integer> ids) {
        TypedQuery<Author> query = entityManager.createQuery("select a from Author a where a.id IN :ids", Author.class);
        query.setParameter("ids", ids);
        return query.getResultList();
    }

    @Override
    public List<Author> selectByName(String name) {
        TypedQuery<Author> query = entityManager.createQuery("select a from Author a where lower(a.name) like :name", Author.class);
        query.setParameter("name", "%" + name.toLowerCase() + "%");
        return query.getResultList();
    }
}
