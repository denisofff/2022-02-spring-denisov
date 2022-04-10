package ru.otus.homework.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.otus.homework.config.AppConfig;
import ru.otus.homework.files.FileReaderImpl;

import java.util.List;

@DisplayName("Check class TestDaoImplTest")
class TestDaoImplTest {
    public static final String TEST_FILE_NAME = "questions.csv";

    private final List<String> testQuestions = List.of(new String[] {"Код вопроса;Текст вопроса;Коды ответов;Тексты ответов;Код правильного ответа",
            "Q1;Вопрос;A,B,C,D;1,2,3,4;D"});

    TestDaoImpl dao;

    @BeforeEach
    void loadQuestions() {
        var appConfig = Mockito.mock(AppConfig.class);
        var fileReader = Mockito.mock(FileReaderImpl.class);

        Mockito.when(appConfig.getLocale()).thenReturn("ru-RU");
        Mockito.when(fileReader.readFromFile(appConfig.getLocale() + "_" + TEST_FILE_NAME)).thenReturn(testQuestions);

        dao = new TestDaoImpl(appConfig, fileReader, TEST_FILE_NAME);
        dao.load();
    }

    @DisplayName("Checking count of loading messages")
    @Test
    void checkCountLoadedMessages() {
        Assertions.assertEquals(dao.getQuestions().size(), 1);
    }

    @DisplayName("Checking test of first question")
    @Test
    void checkTestOfFirstMessage() {
        Assertions.assertEquals(dao.getQuestions().get(0).getText(), "Вопрос");
    }
}