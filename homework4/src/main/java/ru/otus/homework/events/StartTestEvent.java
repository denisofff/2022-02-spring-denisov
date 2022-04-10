package ru.otus.homework.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

public class StartTestEvent extends ApplicationEvent {
    public StartTestEvent(Object source) {
        super(source);
    }
}
