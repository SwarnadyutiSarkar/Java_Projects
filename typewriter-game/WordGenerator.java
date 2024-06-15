package com.typewritergame;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

public class WordGenerator {

    private final List<String> words;
    private final Random random;

    public WordGenerator() {
        words = loadWords();
        random = new Random();
    }

    private List<String> loadWords() {
        try {
            return Files.readAllLines(Paths.get("src/main/resources/words.txt"));
        } catch (IOException e) {
            throw new RuntimeException("Unable to load words file", e);
        }
    }

    public String generateWord() {
        return words.get(random.nextInt(words.size()));
    }
}
