package ru.otus.homework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import ru.otus.homework.service.TestService;

@SpringBootApplication
public class Homework3Application {

	public static void main(String[] args) {
		var context = SpringApplication.run(Homework3Application.class, args);

		TestService service = context.getBean(TestService.class);
		service.startTest();
	}

}
