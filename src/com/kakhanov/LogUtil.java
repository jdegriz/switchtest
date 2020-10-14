package com.kakhanov;

import java.util.Arrays;
import java.util.function.Function;

public class LogUtil {
    public static void outputLapse(String name, String timeText, int count, long nanoTime,
                                    Function<Long, Long> timeConverter) {
        long nanosBeforeStringConcat = System.nanoTime();

        System.out.println(name + " count: " + count + "; " + name.toLowerCase() +
                " time (" + timeText + "): " +
                timeConverter.apply(nanosBeforeStringConcat - nanoTime));
    }

    public static void outputLapse(String name, String timeText, int count, long nanoTime,
                                    Function<Long, Long> timeConverter, boolean compareResults) {
        outputLapse(name, timeText, count, nanoTime, timeConverter, compareResults, null, null);
    }

    public static void outputLapse(String name, String timeText, int count, long nanoTime,
                                    Function<Long, Long> timeConverter, boolean compareResults,
                                    boolean[] templateResults, boolean[] currentResults) {
        long nanosBeforeStringConcat = System.nanoTime();

        System.out.println(name + " count: " + count + "; " + name.toLowerCase() +
                " time (" + timeText + "): " +
                timeConverter.apply(nanosBeforeStringConcat - nanoTime) +
                (compareResults ? ("; results: " + Arrays.equals(templateResults, currentResults)) : ""));
    }
}
