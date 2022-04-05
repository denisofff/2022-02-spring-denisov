package ru.otus.homework.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.homework.events.StartTestEvent;

@ShellComponent
@RequiredArgsConstructor
public class ShellCommands {

    private final ApplicationEventPublisher eventsPublisher;

    @ShellMethod(value = "Start test command", key = {"s", "start"})
    public void start() {
        eventsPublisher.publishEvent(new StartTestEvent(this));
    }
}
