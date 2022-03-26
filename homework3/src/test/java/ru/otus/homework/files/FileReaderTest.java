package ru.otus.homework.files;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Check class FileReaderTest")
class FileReaderTest {
    FileReader fileReader = new FileReader();

    @Test
    void testNotExistingFile() {
        Assertions.assertEquals(fileReader.readFromFile("not existing file name"), null);
    }
}