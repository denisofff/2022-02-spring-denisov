package ru.otus.homework.dao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.otus.homework.config.AppConfig;
import ru.otus.homework.domain.Answer;
import ru.otus.homework.domain.Question;
import ru.otus.homework.files.FileReader;

import java.util.ArrayList;
import java.util.List;

@Component
public class TestDaoImpl implements TestDao {
    private static final String COMMA_SEPARATOR = ",";
    private static final String SEMICOLON_SEPARATOR = ";";

    private final AppConfig appConfig;
    private final FileReader fileReader;

    private final List<Question> questions = new ArrayList<>();
    private final String fileName;

    public TestDaoImpl(AppConfig appConfig, FileReader fileReader, @Value("${questions.filename}") String fileName) {
        this.appConfig = appConfig;
        this.fileReader = fileReader;
        this.fileName = fileName;
    }

    @Override
    public void load() {
        var lines = fileReader.readFromFile(appConfig.getLocale() + "_" + fileName);

        boolean isFirstRow = true;
        for (var line : lines) {
            if (isFirstRow) {
                isFirstRow = false;
                continue;
            }

            var rows = line.split(SEMICOLON_SEPARATOR);

            Question questionDao = new Question(rows[0], rows[1]);

            var answersCodes = rows[2].split(COMMA_SEPARATOR);
            var answersTexts = rows[3].split(COMMA_SEPARATOR);

            for (int item = 0; item < answersCodes.length; item++) {
                questionDao.getAnswers().add(new Answer(answersCodes[item], answersTexts[item], answersCodes[item].equals(rows[4])));
            }

            questions.add(questionDao);
        }
    }

    @Override
    public List<Question> getQuestions() {
        return questions;
    }
}
