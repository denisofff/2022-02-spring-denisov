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
        return name == null ? genreDao.selectAll() : genreDao.selectByName(name);
    }

    @Override
    @Transactional(readOnly = true)
    public Genre getGenre(int id) {
        return genreDao.get(id);
    }

    @Override
    @Transactional
    public void insertGenre(Genre genre) {
        genreDao.insert(genre);
    }

    @Override
    @Transactional
    public void updateGenre(Genre genre) {
        genreDao.update(genre);
    }

    @Override
    @Transactional
    public void deleteGenre(int id) {
        genreDao.delete(id);
    }
}
