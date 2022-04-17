package ru.book.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.book.domain.Genre;
import ru.book.repository.GenreRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreDao;

    @Override
    @Transactional(readOnly = true)
    public List<Genre> findGenres(String name) {
        return name == null ? genreDao.findAll() : genreDao.findByNameContainsIgnoreCase(name);
    }

    @Override
    @Transactional(readOnly = true)
    public Genre getGenre(int id) {
        return genreDao.findById(id).orElseThrow();
    }

    @Override
    @Transactional
    public void insertGenre(Genre genre) {
        genreDao.save(genre);
    }

    @Override
    @Transactional
    public void updateGenre(Genre genre) {
        genreDao.save(genre);
    }

    @Override
    @Transactional
    public void deleteGenre(int id) {
        genreDao.delete(getGenre(id));
    }
}
