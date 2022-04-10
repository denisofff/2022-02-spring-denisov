package ru.otus.homework;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import ru.otus.homework.service.TestService;

@SpringBootApplication
public class Homework3Application {
	public static void main(String[] args) {
		SpringApplication.run(Homework3Application.class, args);
	}
}
