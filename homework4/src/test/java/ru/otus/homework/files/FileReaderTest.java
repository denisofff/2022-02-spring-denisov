package ru.otus.homework.files;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Check class FileReaderTest")
class FileReaderTest {
    FileReaderImpl fileReader = new FileReaderImpl();

    @Test
    void testNotExistingFile() {
        Assertions.assertEquals(fileReader.readFromFile("not existing file name"), null);
    }
}