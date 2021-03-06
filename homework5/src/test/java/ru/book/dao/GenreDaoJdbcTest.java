package ru.book.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.book.domain.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Dao для работы с жанрами")
@JdbcTest
@Import(GenreDaoJdbc.class)
class GenreDaoJdbcTest {
    private static final String GENRE_NAME = "Любовный роман";
    private static final String EXISTING_GENRE_NAME = "Роман-Антиутопия";
    private static final int EXISTING_GENRE_ID = 3;
    
    @Autowired
    private GenreDaoJdbc genreDao;

    @DisplayName("Добавление жанра и проверка поиска по имени")
    @Test
    void shouldInsertGenre() {
        Genre expectedGenre = new Genre(null, GENRE_NAME);
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
        genreDao.update(new Genre(EXISTING_GENRE_ID, GENRE_NAME));
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
        assertThatCode(() -> genreDao.get(EXISTING_GENRE_ID))
                .doesNotThrowAnyException();

        genreDao.delete(EXISTING_GENRE_ID);

        assertThatThrownBy(() -> genreDao.get(EXISTING_GENRE_ID))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }

    @DisplayName("Проверка ожидаемого списка жанров")
    @Test
    void shouldReturnExpectedGenresList() {
        Genre expectedGenre = new Genre(EXISTING_GENRE_ID, EXISTING_GENRE_NAME);
        List<Genre> actualGenreList = genreDao.selectAll();
        assertThat(actualGenreList).contains(expectedGenre);
    }
}