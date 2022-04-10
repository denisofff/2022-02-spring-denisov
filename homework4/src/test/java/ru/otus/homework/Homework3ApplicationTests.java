package ru.otus.homework;

import lombok.Getter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import ru.otus.homework.config.AppConfig;
import ru.otus.homework.dao.TestDao;
import ru.otus.homework.dao.TestDaoImpl;
import ru.otus.homework.files.FileReaderImpl;
import ru.otus.homework.service.IOService;
import ru.otus.homework.service.IOServiceImpl;
import ru.otus.homework.service.TestService;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

@DisplayName("SpringBootTest")
@SpringBootTest
class Homework3ApplicationTests {
	@Autowired
	TestDao dao;

	@Autowired
	TestService testService;

	@Autowired
	IOService ioService;

	@TestConfiguration
	static class TestConfig {
		public class IOServiceByteArray implements IOService {
			private final PrintStream output;
			private final Scanner input;
			@Getter
			private final ByteArrayOutputStream byteArrayOutputStream;

			public IOServiceByteArray(InputStream inputStream, ByteArrayOutputStream byteArrayOutputStream) {
				this.byteArrayOutputStream = byteArrayOutputStream;
				this.output = new PrintStream(byteArrayOutputStream);
				this.input = new Scanner(inputStream);
			}

			@Override
			public String readString() {
				return input.nextLine();
			}

			@Override
			public void outputString(String s) {
				output.println(s);
			}
		}

		@Bean
		public IOService ioService () {
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			InputStream bis = new ByteArrayInputStream(new byte [] {'A', '\r', 'A', '\r', 'A', '\r', 'A', '\r', 'A', '\r'});

			return new IOServiceByteArray(bis, byteArrayOutputStream);
		}
	}

	@DisplayName("Test successfully passed")
	@Test
	void checkCountLoadedMessages() {
		testService.startTest();

		ByteArrayOutputStream byteArrayOutputStream = ((TestConfig.IOServiceByteArray) ioService).getByteArrayOutputStream();

		var resultLines = Arrays.stream(((TestConfig.IOServiceByteArray) ioService).byteArrayOutputStream.toString().split("\n")).toList();

		// последний вывод - результат теста
		Assertions.assertTrue(resultLines.get(resultLines.size() - 1).startsWith("Поздравляю"));
	}

	@DisplayName("Testing dao first question")
	@Test
	void checkTestOfFirstMessage() {
		Assertions.assertEquals(dao.getQuestions().get(0).getText(), "Сколько ног у слона?");
	}
}
