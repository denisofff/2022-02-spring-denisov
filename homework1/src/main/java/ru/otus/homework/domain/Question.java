package ru.otus.homework.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Getter
@ToString
public class Question {
    /**
     * Код вопросы
     */
    private final String code;
    /**
     * Текст вопросы
     */
    private final String text;

    /**
     * Список вопросов
     */
    private final List<Answer> answers = new ArrayList<Answer>();
}
