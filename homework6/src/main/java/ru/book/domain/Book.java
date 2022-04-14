package ru.book.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @Column(name = "Name")
    private String name;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "Booksgenres",
            joinColumns = @JoinColumn(name = "Book_Id"),
            inverseJoinColumns = @JoinColumn(name = "Genre_Id")
    )
    @Fetch(FetchMode.SUBSELECT)
    private List<Genre> genres = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "Booksauthors",
            joinColumns = @JoinColumn(name = "Book_Id"),
            inverseJoinColumns = @JoinColumn(name = "Author_Id")
    )
    @Fetch(FetchMode.SUBSELECT)
    private List<Author> authors = new ArrayList<>();
}
