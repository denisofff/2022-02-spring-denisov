package ru.otus.homework.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.otus.homework.dao.TestDao;
import ru.otus.homework.service.TestService;

@Configuration
@PropertySource("classpath:application.properties")
public class ServicesConfig {

    @Value("${passing_score}")
    private int passingScore;

    @Bean
    public TestService testService(TestDao dao) {
        return new TestService(dao, passingScore);
    }
}
