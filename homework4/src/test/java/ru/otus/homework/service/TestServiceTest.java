package ru.otus.homework.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.context.MessageSource;
import ru.otus.homework.config.AppConfig;
import ru.otus.homework.dao.TestDao;
import ru.otus.homework.dao.TestDaoImpl;
import ru.otus.homework.files.FileReaderImpl;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@DisplayName("Check class TestServiceTest")
public class TestServiceTest {
    public static final String TEST_FILE_NAME = "questions.csv";

    private final List<String> testQuestions = List.of(new String[]{"Код вопроса;Текст вопроса;Коды ответов;Тексты ответов;Код правильного ответа",
            "Q1;Вопрос;A,B,C,D;1,2,3,4;D"});
    private final int passingScore = 1;

    TestServiceImpl testService;
    TestDaoImpl testDao;
    MessageSource messageSource;
    AppConfig appConfig;
    IOService ioService;

    @BeforeEach
    void loadQuestions() {
        appConfig = Mockito.mock(AppConfig.class);
        var fileReader = Mockito.mock(FileReaderImpl.class);
        messageSource = Mockito.mock(MessageSource.class);

        testDao = new TestDaoImpl(appConfig, fileReader, TEST_FILE_NAME);
        testDao.load();

        Mockito.when(appConfig.getLocale()).thenReturn("ru-RU");
        Mockito.when(fileReader.readFromFile(appConfig.getLocale() + "_" + TEST_FILE_NAME)).thenReturn(testQuestions);
        Mockito.when(messageSource.getMessage("strings.test_passed", new Object[] {1}, Locale.forLanguageTag(appConfig.getLocale()))).thenReturn("Успех");
        Mockito.when(messageSource.getMessage("strings.test_failed", new Object[] {0, 1}, Locale.forLanguageTag(appConfig.getLocale()))).thenReturn("Провал");


    }

    @DisplayName("Checking test of success question")
    @Test
    void checkSuccessTestMessage() {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(bos);
        InputStream bis = new ByteArrayInputStream(new byte [] {'D'});
        ioService = new IOServiceImpl(ps, bis);
        testService = new TestServiceImpl(testDao, messageSource, appConfig, ioService, passingScore);

        testService.startTest();

        var resultLines = Arrays.stream(bos.toString().split("\n")).toList();

        Assertions.assertEquals(resultLines.get(resultLines.size() - 1), "Успех\r");
    }

    @DisplayName("Checking test of failed question")
    @Test
    void checkFailedTestMessage() {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(bos);
        InputStream bis = new ByteArrayInputStream(new byte [] {'A'});
        ioService = new IOServiceImpl(ps, bis);
        testService = new TestServiceImpl(testDao, messageSource, appConfig, ioService, passingScore);

        testService.startTest();

        var resultLines = Arrays.stream(bos.toString().split("\n")).toList();

        Assertions.assertEquals(resultLines.get(resultLines.size() - 1), "Провал\r");
    }
}
