package ru.book.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.book.domain.Genre;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.Arrays;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class GenreRepositoryJpa implements GenreRepository {
    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public void insert(Genre genre) {
        entityManager.persist(genre);
    }

    @Override
    public void update(Genre genre) {
        entityManager.merge(genre);
    }

    @Override
    public void delete(int id) {
        Query query = entityManager.createQuery("delete from Genre a where a.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public Genre get(int id) {
        return entityManager.find(Genre.class, id);
    }

    @Override
    public List<Genre> selectAll() {
        return entityManager.createQuery("select a from Genre a", Genre.class).getResultList();
    }

    @Override
    public List<Genre> selectByIds(List<Integer> ids) {
        TypedQuery<Genre> query = entityManager.createQuery("select a from Genre a where a.id IN :ids", Genre.class);
        query.setParameter("ids", ids);
        return query.getResultList();
    }

    @Override
    public List<Genre> selectByName(String name) {
        TypedQuery<Genre> query = entityManager.createQuery("select a from Genre a where lower(a.name) like :name", Genre.class);
        query.setParameter("name", "%" + name.toLowerCase() + "%");
        return query.getResultList();
    }
}
