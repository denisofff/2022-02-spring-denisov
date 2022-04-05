package ru.otus.homework.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.homework.service.IOService;
import ru.otus.homework.service.IOServiceImpl;

@Configuration
public class AppConfig {
    @Getter
    private final String locale;

    public AppConfig(@Value("${locale.code}") String locale) {
        this.locale = locale;
    }

    @Bean
    public IOService ioService() {
        return new IOServiceImpl(System.out, System.in);
    }
}
