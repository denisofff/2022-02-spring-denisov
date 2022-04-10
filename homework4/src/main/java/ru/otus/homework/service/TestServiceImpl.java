package ru.otus.homework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import ru.otus.homework.config.AppConfig;
import ru.otus.homework.dao.TestDao;
import ru.otus.homework.domain.Answer;
import ru.otus.homework.events.StartTestEvent;

import java.util.Locale;
import java.util.Scanner;

@Service
public class TestServiceImpl implements TestService {
    private final TestDao testDao;
    private final MessageSource messageSource;
    private final AppConfig appConfig;
    private final IOService ioService;

    private final int passingScore;

    @Autowired
    public TestServiceImpl(TestDao testDao, MessageSource messageSource, AppConfig appConfig, IOService ioService, @Value("${result.passing_score}") int passingScore) {
        this.testDao = testDao;
        this.messageSource = messageSource;
        this.appConfig = appConfig;
        this.ioService = ioService;
        this.passingScore = passingScore;
    }

    @Override
    public void startTest() {
        testDao.load();
        var correctAnswers = printQuestionsAndCalculateCorrectAnswers();

        ioService.outputString(testResultMessage(correctAnswers));
    }

    private int printQuestionsAndCalculateCorrectAnswers() {
        int correctAnswers = 0;
        String userAnswer = "";

        for (var question : testDao.getQuestions()) {
            ioService.outputString(question.getCode() + ". " + question.getText());
            for (var answer : question.getAnswers()) {
                ioService.outputString(answer.getCode() + ") " + answer.getText());
            }


            while (true) {
                userAnswer = ioService.readString();
                String finalUserAnswer = userAnswer;
                if (question.getAnswers().stream().anyMatch(t -> t.getCode().equals(finalUserAnswer))) {
                    break;
                }
                ioService.outputString(messageSource.getMessage("strings.wrong_answer", null, Locale.forLanguageTag(appConfig.getLocale())));
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
