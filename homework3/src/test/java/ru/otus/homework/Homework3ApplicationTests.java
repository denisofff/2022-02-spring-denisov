package ru.otus.homework;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.homework.config.AppConfig;
import ru.otus.homework.dao.TestDaoImpl;
import ru.otus.homework.files.FileReader;

import java.util.List;

@DisplayName("SpringBootTest")
@SpringBootTest
class Homework3ApplicationTests {
	@MockBean
	AppConfig appConfig;

	@MockBean
	FileReader fileReader;

	public static final String TEST_FILE_NAME = "questions.csv";

	private final List<String> testQuestions = List.of(new String[] {"Код вопроса;Текст вопроса;Коды ответов;Тексты ответов;Код правильного ответа",
			"Q1;Вопрос;A,B,C,D;1,2,3,4;D"});

	TestDaoImpl dao;

	@BeforeEach
	void loadQuestions() {
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
