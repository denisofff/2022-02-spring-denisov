package ru.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.book.domain.Book;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {
    List<Book> findByNameContainsIgnoreCase(String name);

    @Query(value = "select b from Book b join b.genres g where lower(g.name) like concat(concat('%', lower(:name)), '%')")
    List<Book> findByGenre(@Param("name") String name);

    @Query(value = "select b from Book b join b.authors a where lower(a.name) like concat(concat('%', lower(:name)), '%')")
    List<Book> findByAuthor(@Param("name") String name);
}
