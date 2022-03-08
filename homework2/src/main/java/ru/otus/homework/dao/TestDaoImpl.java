package ru.otus.homework.dao;

import org.springframework.beans.factory.annotation.Value;
import ru.otus.homework.domain.Answer;
import ru.otus.homework.domain.Question;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class TestDaoImpl implements TestDao {
    private static final String COMMA_SEPARATOR = ",";
    private static final String SEMICOLON_SEPARATOR = ";";

    private final List<Question> questions = new ArrayList<>();
    private final String fileName;

    public TestDaoImpl(String fileName) {
        this.fileName = fileName;
        load();
    }

    public List<String> readFromFile(String fileName) {
        List<String> lines = new ArrayList<>();
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();

        try (InputStream inputStream = classLoader.getResourceAsStream(fileName)) {
            if (inputStream == null)
                return null;

            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lines;
    }

    @Override
    public void load() {
        var lines = readFromFile(fileName);

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
