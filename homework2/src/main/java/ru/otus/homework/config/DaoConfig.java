package ru.otus.homework.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.otus.homework.dao.TestDao;
import ru.otus.homework.dao.TestDaoImpl;

@Configuration
@PropertySource("classpath:application.properties")
public class DaoConfig {

    @Value("${questions_file_name}")
    private String fileName;

    @Bean
    public TestDao testDao() {
        return new TestDaoImpl(fileName);
    }
}
