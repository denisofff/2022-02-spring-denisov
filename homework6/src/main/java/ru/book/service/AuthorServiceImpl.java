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
        return name == null ? authorDao.selectAll() : authorDao.selectByName(name);
    }

    @Override
    @Transactional(readOnly = true)
    public Author getAuthor(int id) {
        return authorDao.get(id);
    }

    @Override
    @Transactional
    public void insertAuthor(Author author) {
        authorDao.insert(author);
    }

    @Override
    @Transactional
    public void updateAuthor(Author author) {
        authorDao.update(author);
    }

    @Override
    @Transactional
    public void deleteAuthor(int id) {
        authorDao.delete(id);
    }
}
