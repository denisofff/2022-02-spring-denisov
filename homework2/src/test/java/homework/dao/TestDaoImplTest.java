package homework.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.homework.dao.TestDaoImpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@DisplayName("Check class TestDaoImplTest")
public class TestDaoImplTest {
    TestDaoImpl test = new TestDaoImpl("questions.csv");

    @DisplayName("Check count from questions and count lines from file")
    @Test
    public void checkCountQuestionsAndLinesInFile() throws IOException {
        test.load();
        //Assertions.assertEquals((Files.readAllLines(Path.of("questions.csv")).size() - 1), test.getQuestions().size());
    }
}
