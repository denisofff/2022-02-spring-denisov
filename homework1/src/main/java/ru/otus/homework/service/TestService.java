package ru.otus.homework.service;

import ru.otus.homework.dao.TestDao;

public class TestService {
    private final TestDao testDao;

    public TestService(TestDao testDao) {
        this.testDao = testDao;
    }

    public void testStart() {
        //Scanner keyboard = new Scanner(System.in);

        for (var question : testDao.getQuestions()) {
            System.out.println(question.getCode() + ". " + question.getText());
            for (var answer : question.getAnswers()) {
                System.out.println(answer.getCode() + ") " + answer.getText());
            }


            /*
            while (true) {
                String userAnswer = keyboard.next();
                if (question.getAnswers().stream().anyMatch(t -> t.getCode().equals(userAnswer))) {
                    break;
                }
                System.out.println("Wrong answer! Try again");
            }
            */
        }

        //System.out.println("Congratulations! You are the winner!");
    }
}
