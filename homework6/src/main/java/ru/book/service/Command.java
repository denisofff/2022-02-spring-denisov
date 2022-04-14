package ru.book.service;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.book.domain.Author;
import ru.book.domain.Book;
import ru.book.domain.BookComment;
import ru.book.domain.Genre;

import java.time.LocalDate;
import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class Command {
    private final BookService bookService;
    private final AuthorService authorService;
    private final GenreService genreService;
    private final BookCommentService bookCommentService;

    //===============================================================
    // Справочник авторов
    //===============================================================

    @ShellMethod(value = "Добавить автора", key = {"ia"})
    public void authorInsert(@ShellOption String name) {
        authorService.insertAuthor(new Author(null, name));
    }

    @ShellMethod(value = "Редактировать автора", key = {"ua"})
    public void authorUpdate(@ShellOption int id, @ShellOption String name) {
        authorService.updateAuthor(new Author(id, name));
    }

    @ShellMethod(value = "Удалить автора", key = {"da"})
    public void authorDelete(int id) {
        authorService.deleteAuthor(id);
    }

    @ShellMethod(value = "Поиск автора", key = {"sa"})
    public void authorSelect(@ShellOption String name) {
        authorsPrintInternal(authorService.findAuthors(name));
    }

    @ShellMethod(value = "Вывести весь список авторов", key = {"pa"})
    public void authorPrint() {
        authorsPrintInternal(authorService.findAuthors(null));
    }

    //===============================================================
    // Справочник жанров
    //===============================================================

    @ShellMethod(value = "Добавить жанр", key = {"ig"})
    public void genreInsert(@ShellOption String name) {
        genreService.insertGenre(new Genre(null, name));
    }

    @ShellMethod(value = "Редактировать жанр", key = {"ug"})
    public void genreUpdate(@ShellOption int id, @ShellOption String name) {
        genreService.updateGenre(new Genre(id, name));
    }

    @ShellMethod(value = "Удалить жанр", key = {"dg"})
    public void genreDelete(int id) {
        genreService.deleteGenre(id);
    }

    @ShellMethod(value = "Поиск жанра", key = {"sg"})
    public void genreSelect(@ShellOption String name) {
        genresPrintInternal(genreService.findGenres(name));
    }

    @ShellMethod(value = "Вывести весь список жанров", key = {"pg"})
    public void genrePrint() {
        genresPrintInternal(genreService.findGenres(null));
    }

    //===============================================================
    // Книги
    //===============================================================

    @ShellMethod(value = "Добавить книгу", key = {"ib"})
    public void bookInsert(@ShellOption String name, @ShellOption String genresIds, @ShellOption String authorsIds) {
        bookService.insertBook(name, genresIds, authorsIds);
    }

    @ShellMethod(value = "Редактировать книгу", key = {"ub"})
    public void bookUpdate(@ShellOption int id, @ShellOption String name, @ShellOption String genresIds, @ShellOption String authorsIds) {
        bookService.updateBook(id, name, genresIds, authorsIds);
    }

    @ShellMethod(value = "Удалить книгу", key = {"db"})
    public void bookDelete(int id) {
        bookService.deleteBook(id);
    }

    @ShellMethod(value = "Поиск книги по названию", key = {"sbn"})
    public void bookSelectByName(@ShellOption String name) {
        booksPrintInternal(bookService.findBooksByName(name));
    }

    @ShellMethod(value = "Поиск книги по жанру", key = {"sbg"})
    public void bookSelectByGenre(@ShellOption String name) {
        booksPrintInternal(bookService.findBooksByGenre(name));
    }

    @ShellMethod(value = "Поиск книги по автору", key = {"sba"})
    public void bookSelectByAuthor(@ShellOption String name) {
        booksPrintInternal(bookService.findBooksByAuthor(name));
    }

    @ShellMethod(value = "Вывести весь список книг", key = {"pb"})
    public void bookPrint() {
        booksPrintInternal(bookService.findBooksByName(null));
    }

    //===============================================================
    // Комментарии
    //===============================================================

    @ShellMethod(value = "Добавить комментарий к книге", key = {"ic"})
    public void bookCommentInsert(@ShellOption int bookId, @ShellOption String date, @ShellOption String comment) {
        bookCommentService.insertBookComment(new BookComment(null, bookService.getBook(bookId), LocalDate.parse(date), comment));
    }

    @ShellMethod(value = "Редактировать комментарий к книге", key = {"uc"})
    public void bookCommentUpdate(@ShellOption int id, @ShellOption String date, @ShellOption String comment) {
        bookCommentService.updateBookComment(new BookComment(id, bookCommentService.getBookComment(id).getBook(), LocalDate.parse(date), comment));
    }

    @ShellMethod(value = "Удалить комментарий к книге", key = {"dc"})
    public void bookCommentDelete(int id) {
        bookCommentService.deleteBookComment(id);
    }

    @ShellMethod(value = "Просмотр комментариев к книге", key = {"pc"})
    public void bookCommentsPrint(@ShellOption int bookId) {
        booksCommentsPrintInternal(bookCommentService.findByBook(bookService.getBook(bookId)));
    }

    //===============================================================
    // Internal-методы
    //===============================================================

    private void authorsPrintInternal(List<Author> authors) {
        System.out.println("Список авторов:");
        for (var author : authors) {
            System.out.println(author);
        }
    }

    private void genresPrintInternal(List<Genre> genres) {
        System.out.println("Список жанров:");
        for (var genre : genres) {
            System.out.println(genre);
        }
    }

    private void booksPrintInternal(List<Book> books) {
        System.out.println("Список книг:");
        for (var book : books) {
            System.out.println(book);
        }
    }

    private void booksCommentsPrintInternal(List<BookComment> booksComments) {
        System.out.println("Список комментариев:");
        for (var bookComment : booksComments) {
            System.out.println(bookComment);
        }
    }
}
