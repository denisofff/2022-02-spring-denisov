package homework.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.homework.dao.TestDaoImpl;

@DisplayName("Check class TestDaoImplTest")
public class TestDaoImplTest {
    TestDaoImpl test = new TestDaoImpl("questions.csv");

    @DisplayName("Check count from questions and count lines from file")
    @Test
    public void checkCountQuestionsAndLinesInFile() {
        Assertions.assertEquals(test.readFromFile("questions.csv").size() - 1, test.getQuestions().size());
    }
}
