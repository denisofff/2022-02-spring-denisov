package ru.otus.homework.events;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ru.otus.homework.service.TestService;

@Component
@RequiredArgsConstructor
public class TestEventProcessor {
    private final TestService testService;

    @EventListener(StartTestEvent.class)
    public void startTestEvent() {
        testService.startTest();
    }
}
