package ru.otus.homework.service;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cglib.core.Local;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.homework.config.AppConfig;
import ru.otus.homework.dao.TestDao;
import ru.otus.homework.domain.Answer;

import java.util.Locale;
import java.util.Scanner;

@Service
public class TestServiceImpl implements TestService {
    private final TestDao testDao;
    private final MessageSource messageSource;
    private final AppConfig appConfig;

    private final int passingScore;

    @Autowired
    public TestServiceImpl(TestDao testDao, MessageSource messageSource, AppConfig appConfig, @Value("${result.passing_score}") int passingScore) {
        this.testDao = testDao;
        this.messageSource = messageSource;
        this.appConfig = appConfig;
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
                System.out.println(messageSource.getMessage("strings.wrong_answer", null, Locale.forLanguageTag(appConfig.getLocale())));
            }

            if (userAnswer.equals(question.getAnswers().stream().filter(Answer::getIsCorrect).findFirst().get().getCode())) {
                correctAnswers++;
            }
        }

        return correctAnswers;
    }

    private String testResultMessage(int correctAnswers) {
        if (correctAnswers >= passingScore) {
            return messageSource.getMessage("strings.test_passed", new Object[] {correctAnswers}, Locale.forLanguageTag(appConfig.getLocale()));
        } else {
            return messageSource.getMessage("strings.test_failed", new Object[] {correctAnswers, passingScore}, Locale.forLanguageTag(appConfig.getLocale()));
        }
    }
}
