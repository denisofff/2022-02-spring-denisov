package ru.book.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.book.domain.Genre;
import ru.book.repository.GenreRepositoryJpa;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Dao для работы с жанрами")
@DataJpaTest
@Import(GenreRepositoryJpa.class)
class GenreRepositoryJpaTest {
    private static final String GENRE_NAME = "Любовный роман";
    private static final String EXISTING_GENRE_NAME = "Роман-Антиутопия";
    private static final int EXISTING_GENRE_ID = 3;

    @Autowired
    private GenreRepositoryJpa genreDao;

    @Autowired
    private TestEntityManager entityManager;

    @DisplayName("Добавление жанра и проверка поиска по имени")
    @Test
    void shouldInsertGenre() {
        Genre expectedGenre = new Genre().setName(GENRE_NAME);
        var notExistGenre = genreDao.selectByName(GENRE_NAME);
        assertThat(notExistGenre.size()).isEqualTo(0);
        genreDao.insert(expectedGenre);
        var foundedGenre = genreDao.selectByName(GENRE_NAME);
        assertThat(foundedGenre.get(0).getName()).isEqualTo(GENRE_NAME);
    }

    @DisplayName("Изменение жанра и проверка поиска по имени")
    @Test
    void shouldUpdateGenre() {
        var existingGenre = genreDao.get(EXISTING_GENRE_ID);
        assertThat(existingGenre.getName()).isEqualTo(EXISTING_GENRE_NAME);
        genreDao.update(new Genre().setId(EXISTING_GENRE_ID).setName(GENRE_NAME));
        var updatedGenre = genreDao.get(EXISTING_GENRE_ID);
        assertThat(updatedGenre.getName()).isEqualTo(GENRE_NAME);
    }

    @DisplayName("Возвращать жанра по id")
    @Test
    void shouldReturnExpectedGenreById() {
        var existingGenre = genreDao.get(EXISTING_GENRE_ID);
        assertThat(existingGenre.getName()).isEqualTo(EXISTING_GENRE_NAME);
    }

    @DisplayName("Удаление жанра по id")
    @Test
    void shouldCorrectDeleteGenreById() {
        var existingGenre = genreDao.get(EXISTING_GENRE_ID);

        entityManager.detach(existingGenre);
        genreDao.delete(EXISTING_GENRE_ID);

        assertThat(genreDao.get(EXISTING_GENRE_ID)).isEqualTo(null);
    }

    @DisplayName("Проверка ожидаемого списка жанров")
    @Test
    void shouldReturnExpectedGenresList() {
        Genre expectedGenre = new Genre().setId(EXISTING_GENRE_ID).setName(EXISTING_GENRE_NAME);
        List<Genre> actualGenreList = genreDao.selectAll();
        assertThat(actualGenreList).contains(expectedGenre);
    }
}