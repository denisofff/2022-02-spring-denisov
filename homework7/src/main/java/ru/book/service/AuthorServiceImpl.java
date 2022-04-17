package ru.book.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.book.domain.Author;
import ru.book.repository.AuthorRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorDao;

    @Override
    @Transactional(readOnly = true)
    public List<Author> findAuthors(String name) {
        return name == null ? authorDao.findAll() : authorDao.findByNameContainsIgnoreCase(name);
    }

    @Override
    @Transactional(readOnly = true)
    public Author getAuthor(int id) {
        return authorDao.findById(id).orElseThrow();
    }

    @Override
    @Transactional
    public void insertAuthor(Author author) {
        authorDao.save(author);
    }

    @Override
    @Transactional
    public void updateAuthor(Author author) {
        authorDao.save(author);
    }

    @Override
    @Transactional
    public void deleteAuthor(int id) {
        authorDao.delete(getAuthor(id));
    }
}
