package com.kakhanov;

import java.util.stream.IntStream;

public class DataUtil {
    public static void clearResults(boolean[] results) {
        IntStream.range(0, results.length).forEach(i -> results[i] = false);
    }
}
