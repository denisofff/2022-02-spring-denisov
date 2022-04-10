package ru.otus.homework.dao;

import ru.otus.homework.domain.Question;

import java.util.List;

public interface TestDao {
    void load();

    List<Question> getQuestions();
}
