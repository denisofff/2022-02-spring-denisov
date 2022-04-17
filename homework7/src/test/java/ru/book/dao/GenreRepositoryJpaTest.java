package ru.book.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.book.domain.Genre;
import ru.book.repository.GenreRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Dao для работы с жанрами")
@DataJpaTest
class GenreRepositoryJpaTest {
    private static final String GENRE_NAME = "Любовный роман";
    private static final String EXISTING_GENRE_NAME = "Роман-Антиутопия";
    private static final int EXISTING_GENRE_ID = 3;

    @Autowired
    private GenreRepository genreDao;

    @Autowired
    private TestEntityManager entityManager;

    @DisplayName("Добавление жанра и проверка поиска по имени")
    @Test
    void shouldInsertGenre() {
        Genre expectedGenre = new Genre(null, GENRE_NAME);
        var notExistGenre = genreDao.findByNameContainsIgnoreCase(GENRE_NAME);
        assertThat(notExistGenre.size()).isEqualTo(0);
        genreDao.save(expectedGenre);
        var foundedGenre = genreDao.findByNameContainsIgnoreCase(GENRE_NAME);
        assertThat(foundedGenre.get(0).getName()).isEqualTo(GENRE_NAME);
    }

    @DisplayName("Изменение жанра и проверка поиска по имени")
    @Test
    void shouldUpdateGenre() {
        var existingGenre = genreDao.getById(EXISTING_GENRE_ID);
        assertThat(existingGenre.getName()).isEqualTo(EXISTING_GENRE_NAME);
        genreDao.save(new Genre(EXISTING_GENRE_ID, GENRE_NAME));
        var updatedGenre = genreDao.getById(EXISTING_GENRE_ID);
        assertThat(updatedGenre.getName()).isEqualTo(GENRE_NAME);
    }

    @DisplayName("Возвращать жанра по id")
    @Test
    void shouldReturnExpectedGenreById() {
        var existingGenre = genreDao.getById(EXISTING_GENRE_ID);
        assertThat(existingGenre.getName()).isEqualTo(EXISTING_GENRE_NAME);
    }

    @DisplayName("Удаление жанра по id")
    @Test
    void shouldCorrectDeleteGenreById() {
        var existingGenre = genreDao.getById(EXISTING_GENRE_ID);

        genreDao.delete(existingGenre);
        entityManager.flush();

        assertThatThrownBy(() -> {
            var foundedGenre = genreDao.getById(EXISTING_GENRE_ID);
            System.out.println(foundedGenre);
        }).isInstanceOf(EntityNotFoundException.class);
    }

    @DisplayName("Проверка ожидаемого списка жанров")
    @Test
    void shouldReturnExpectedGenresList() {
        Genre expectedGenre = new Genre(EXISTING_GENRE_ID, EXISTING_GENRE_NAME);
        List<Genre> actualGenreList = genreDao.findAll();
        assertThat(actualGenreList).contains(expectedGenre);
    }
}