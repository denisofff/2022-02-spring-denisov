package ru.otus.homework.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
@Getter
public class Answer {
    /**
     * Код ответа (может быть, номер, может быть, буквы: A,B,C
     */
    private final String code;
    /**
     * Текст ответа
     */
    private final String text;
    /**
     * Является ли ответ корректным (пока считаем, что тест с единственным вариантом
     */
    private final Boolean isCorrect;
}
