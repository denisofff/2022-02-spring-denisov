package ru.otus.homework.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Getter
    private final String locale;

    public AppConfig(@Value("${locale.code}") String locale) {
        this.locale = locale;
    }
}
