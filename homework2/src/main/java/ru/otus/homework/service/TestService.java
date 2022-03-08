package ru.otus.homework.service;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.homework.dao.TestDao;
import ru.otus.homework.domain.Answer;

import java.util.Scanner;

@Service
public class TestService {
    private final TestDao testDao;
    @Getter
    private final int passingScore;

    @Autowired
    public TestService(TestDao testDao, int passingScore) {
        this.testDao = testDao;
        this.passingScore = passingScore;
    }

    public int printQuestionsAndCalculateCorrectAnswers() {
        int correctAnswers = 0;
        String userAnswer = "";
        Scanner keyboard = new Scanner(System.in);

        for (var question : testDao.getQuestions()) {
            System.out.println(question.getCode() + ". " + question.getText());
            for (var answer : question.getAnswers()) {
                System.out.println(answer.getCode() + ") " + answer.getText());
            }


            while (true) {
                userAnswer = keyboard.next();
                String finalUserAnswer = userAnswer;
                if (question.getAnswers().stream().anyMatch(t -> t.getCode().equals(finalUserAnswer))) {
                    break;
                }
                System.out.println("Wrong answer! Try again");
            }

            if (userAnswer.equals(question.getAnswers().stream().filter(Answer::getIsCorrect).findFirst().get().getCode())) {
                correctAnswers++;
            }
        }

        return correctAnswers;
    }

    public String testResultMessage(int correctAnswers) {
        if (correctAnswers >= passingScore) {
            return "Congratulations! You are pass the test with " + correctAnswers + " correct answers";
        } else {
            return "You fail test. Get " + correctAnswers + " correct answers, but for successful need to get " + passingScore;
        }
    }

    public void startTest() {
        var correctAnswers = printQuestionsAndCalculateCorrectAnswers();

        System.out.println(testResultMessage(correctAnswers));
    }
}
