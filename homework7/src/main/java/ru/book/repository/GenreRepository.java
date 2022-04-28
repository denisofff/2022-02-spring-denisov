package ru.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.book.domain.Genre;

import java.util.List;

public interface GenreRepository extends JpaRepository<Genre, Integer> {
    @Query( "select a from Genre a where id in :ids" )
    List<Genre> findByIds(@Param("ids") List<Integer> ids);

    List<Genre> findByNameContainsIgnoreCase(String name);
}
