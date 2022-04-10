package ru.otus.homework.service;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.homework.dao.TestDao;
import ru.otus.homework.domain.Answer;

import java.util.Scanner;

@Service
public class TestServiceImpl implements TestService {
    private final TestDao testDao;

    @Getter
    private final int passingScore;

    @Autowired
    public TestServiceImpl(TestDao testDao, @Value("${passing_score}") int passingScore) {
        this.testDao = testDao;
        this.passingScore = passingScore;
    }

    @Override
    public void startTest() {
        testDao.load();
        var correctAnswers = printQuestionsAndCalculateCorrectAnswers();

        System.out.println(testResultMessage(correctAnswers));
    }

    private int printQuestionsAndCalculateCorrectAnswers() {
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

    private String testResultMessage(int correctAnswers) {
        if (correctAnswers >= passingScore) {
            return "Congratulations! You are pass the test with " + correctAnswers + " correct answers";
        } else {
            return "You fail test. Get " + correctAnswers + " correct answers, but for successful need to get " + passingScore;
        }
    }
}
