package ru.book.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.book.domain.Author;
import ru.book.domain.Book;
import ru.book.domain.BookComment;
import ru.book.repository.AuthorRepository;
import ru.book.repository.BookCommentRepository;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BookCommentServiceImpl implements BookCommentService {
    private final BookCommentRepository bookCommentDao;

    @Override
    @Transactional(readOnly = true)
    public List<BookComment> findByBook(Book book) {
        return book.getComments();
    }

    @Override
    @Transactional(readOnly = true)
    public BookComment getBookComment(int id) {
        return bookCommentDao.get(id);
    }

    @Override
    @Transactional
    public void insertBookComment(Book book, LocalDate date, String note) {
        bookCommentDao.insert(book, date, note);
    }

    @Override
    @Transactional
    public void updateBookComment(int id, Book book, LocalDate date, String note) {
        //bookCommentDao.update(id, book, date, note);
        var comment = bookCommentDao.get(id);
        comment.setNote(note).setDate(date).setBook(book);
    }

    @Override
    @Transactional
    public void deleteBookComment(int id) {
        bookCommentDao.delete(id);
    }
}
