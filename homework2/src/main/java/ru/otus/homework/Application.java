package ru.otus.homework;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.otus.homework.service.TestService;

@Configuration
@PropertySource("classpath:application.properties")
@ComponentScan
public class Application {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(Application.class);

        TestService service = context.getBean(TestService.class);

        service.startTest();

        context.close();
    }
}
