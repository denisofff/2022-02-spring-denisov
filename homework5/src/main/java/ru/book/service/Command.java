package ru.book.service;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.book.domain.Author;
import ru.book.domain.Book;
import ru.book.domain.Genre;

import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class Command {
    private final Library library;

    //===============================================================
    // Справочник авторов
    //===============================================================

    @ShellMethod(value = "Добавить автора", key = {"ia"})
    public void authorInsert(@ShellOption String name) {
        library.insertAuthor(new Author(null, name));
    }

    @ShellMethod(value = "Редактировать автора", key = {"ua"})
    public void authorUpdate(@ShellOption int id, @ShellOption String name) {
        library.updateAuthor(new Author(id, name));
    }

    @ShellMethod(value = "Удалить автора", key = {"da"})
    public void authorDelete(int id) {
        library.deleteAuthor(id);
    }

    @ShellMethod(value = "Поиск автора", key = {"sa"})
    public void authorSelect(@ShellOption String name) {
        authorsPrintInternal(library.findAuthors(name));
    }

    @ShellMethod(value = "Вывести весь список авторов", key = {"pa"})
    public void authorPrint() {
        authorsPrintInternal(library.findAuthors(null));
    }

    //===============================================================
    // Справочник жанров
    //===============================================================

    @ShellMethod(value = "Добавить жанр", key = {"ig"})
    public void genreInsert(@ShellOption String name) {
        library.insertGenre(new Genre(null, name));
    }

    @ShellMethod(value = "Редактировать жанр", key = {"ug"})
    public void genreUpdate(@ShellOption int id, @ShellOption String name) {
        library.updateGenre(new Genre(id, name));
    }

    @ShellMethod(value = "Удалить жанр", key = {"dg"})
    public void genreDelete(int id) {
        library.deleteGenre(id);
    }

    @ShellMethod(value = "Поиск жанра", key = {"sg"})
    public void genreSelect(@ShellOption String name) {
        genresPrintInternal(library.findGenres(name));
    }

    @ShellMethod(value = "Вывести весь список жанров", key = {"pg"})
    public void genrePrint() {
        genresPrintInternal(library.findGenres(null));
    }

    //===============================================================
    // Книги
    //===============================================================

    @ShellMethod(value = "Добавить книгу", key = {"ib"})
    public void bookInsert(@ShellOption String name, @ShellOption int genreId, @ShellOption int authorId) {
        library.insertBook(new Book(null, name, library.getGenre(genreId), library.getAuthor(authorId)));
    }

    @ShellMethod(value = "Редактировать книгу", key = {"ub"})
    public void bookUpdate(@ShellOption int id, @ShellOption String name, @ShellOption int genreId, @ShellOption int authorId) {
        library.updateBook(new Book(id, name, library.getGenre(genreId), library.getAuthor(authorId)));
    }

    @ShellMethod(value = "Удалить книгу", key = {"db"})
    public void bookDelete(int id) {
        library.deleteBook(id);
    }

    @ShellMethod(value = "Поиск книги по названию", key = {"sbn"})
    public void bookSelectByName(@ShellOption String name) {
        booksPrintInternal(library.findBooksByName(name));
    }

    @ShellMethod(value = "Поиск книги по названию жанра", key = {"sbg"})
    public void bookSelectByGenre(@ShellOption String name) {
        booksPrintInternal(library.findBooksByGenre(name));
    }

    @ShellMethod(value = "Поиск книги по названию жанра", key = {"sba"})
    public void bookSelectByAuthor(@ShellOption String name) {
        booksPrintInternal(library.findBooksByAuthor(name));
    }

    @ShellMethod(value = "Вывести весь список книг", key = {"pb"})
    public void bookPrint() {
        booksPrintInternal(library.findBooksByName(null));
    }

    //===============================================================
    // Internal-методы
    //===============================================================

    private void authorsPrintInternal(List<Author> authors) {
        System.out.println("Список авторов:");
        for (var author: authors) {
            System.out.println(author);
        }
    }

    private void genresPrintInternal(List<Genre> genres) {
        System.out.println("Список жанров:");
        for (var genre: genres) {
            System.out.println(genre);
        }
    }

    private void booksPrintInternal(List<Book> books) {
        System.out.println("Список книг:");
        for (var book: books) {
            System.out.println(book);
        }
    }
}
