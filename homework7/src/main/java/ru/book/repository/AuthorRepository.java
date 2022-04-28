package ru.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.book.domain.Author;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Integer> {
    @Query( "select a from Author a where id in :ids" )
    List<Author> findByIds(@Param("ids") List<Integer> ids);

    List<Author> findByNameContainsIgnoreCase(String name);
}
