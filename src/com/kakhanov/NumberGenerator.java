package com.kakhanov;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

import static com.kakhanov.Constants.*;

public class NumberGenerator {
    private static ThreadLocalRandom random = ThreadLocalRandom.current();

    public static void main(String[] args) throws IOException {
        generateMainNumberFile();
    }

    private static void generateMainNumberFile() throws IOException {
        Files.writeString(Path.of(NUMBERS_FILE_PATH),
                Arrays.toString(generateArray(NUMBER_OF_ITERATIONS, MAX_NUMBER + 1)),
                StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE);
    }

    private static void generateWarmUpNumberFile() throws IOException {
        Files.writeString(Path.of(WARMUP_NUMBERS_FILE_PATH),
                Arrays.toString(generateArray(NUMBER_OF_WARMUP_ITERATIONS, MAX_NUMBER + 1)),
                StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE);
    }

    private static int[] generateArray(int numberOfNumbers, int maxNumber) {
        int[] result = new int[numberOfNumbers];

        IntStream.range(0, numberOfNumbers).forEach(i -> result[i] = random.nextInt(maxNumber));

        return result;
    }
}
