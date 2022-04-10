package ru.otus.homework.domain;

import ru.otus.homework.dao.TestDao;

import java.util.ArrayList;
import java.util.List;

public class Test {
    private final List<Question> questions = new ArrayList<>();

    private final TestDao testDao;

    public Test(TestDao testDao) {
        this.testDao = testDao;
        testDao.load();
    }

    public List<Question> getQuestions() {
        return questions;
    }
}
