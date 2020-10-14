package com.kakhanov;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.IntStream;

public class FileUtil {
    public static int[] readNumbers(String filePath) throws IOException {
        String line = Files.readString(Path.of(filePath));

        String[] numbers = line.split("[^\\d]+");

        int[] result;

        int compensationForLeadingEmpty = numbers[0].isBlank() ? 1 : 0;

        result = new int[numbers.length - compensationForLeadingEmpty];
        IntStream.range(0, numbers.length - compensationForLeadingEmpty)
                .forEach(i -> result[i] = Integer.parseInt(numbers[i + compensationForLeadingEmpty]));

        return result;
    }
}
