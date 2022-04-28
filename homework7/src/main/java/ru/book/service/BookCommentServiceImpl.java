package ru.book.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.book.domain.Book;
import ru.book.domain.BookComment;
import ru.book.repository.BookCommentRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BookCommentServiceImpl implements BookCommentService {
    private final BookCommentRepository bookCommentDao;

    @Override
    public List<BookComment> findByBook(Book book) {
        return bookCommentDao.findByBook(book);
    }

    @Override
    public BookComment getBookComment(int id) {
        return bookCommentDao.findById(id).orElseThrow();
    }

    @Override
    public void insertBookComment(BookComment bookComment) {
        bookCommentDao.save(bookComment);
    }

    @Override
    public void updateBookComment(BookComment bookComment) {
        bookCommentDao.save(bookComment);
    }

    @Override
    public void deleteBookComment(int id) {
        bookCommentDao.delete(getBookComment(id));
    }
}
