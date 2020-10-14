package com.kakhanov;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.IntStream;

import static com.kakhanov.Contants.EXCEPTION_TEXT;

public class Main {
    private static int[] WARMUP_NUMBERS;
    private static int[] NUMBERS;

    private static EvenCalculator calculator = Main::isEvenOptimized;
//    private static EvenCalculator calculator = Main::isEvenSimple;
//    private static EvenCalculator calculator = Main::isEvenSwitch;
//    private static EvenCalculator calculator = Main::isEvenSwitchOptimized;
//    private static EvenCalculator calculator = Main::isEvenIfElse;
//    private static EvenCalculator calculator = Main::isEvenPureIf;

    private static boolean[] firstResults;
    private static boolean[] otherResults;

    public static void main(String[] args) throws IOException {
        WARMUP_NUMBERS = readNumbers(Contants.WARMUP_NUMBERS_FILE_PATH);
        NUMBERS = readNumbers(Contants.NUMBERS_FILE_PATH);
        firstResults = new boolean[NUMBERS.length];
        otherResults = new boolean[NUMBERS.length];

        clearResults(firstResults);
        warmUp();

        System.out.println("");

        IntStream.range(0, Contants.NUMBER_OF_TEST_RUNS)
                .forEach(i -> {
                    if (i == 0) {
                        clearResults(firstResults);
                        test(null, firstResults, false);
                    } else {
                        clearResults(otherResults);
                        test(firstResults, otherResults, true);
                    }
                });
    }

    private static void clearResults(boolean[] results) {
        IntStream.range(0, results.length).forEach(i -> results[i] = false);
    }

    private static void outputLapse(String name, String timeText, int count, long nanoTime,
                                    Function<Long, Long> timeConverter, boolean compareResults) {
        outputLapse(name, timeText, count, nanoTime, timeConverter, compareResults, null, null);
    }

    private static void outputLapse(String name, String timeText, int count, long nanoTime,
                                    Function<Long, Long> timeConverter, boolean compareResults,
                                    boolean[] templateResults, boolean[] currentResults) {
        long nanosBeforeStringConcat = System.nanoTime();

        System.out.println(name + " count: " + count + "; " + name.toLowerCase() +
                " time (" + timeText + "): " +
                timeConverter.apply(nanosBeforeStringConcat - nanoTime) +
                (compareResults ? ("; results: " + Arrays.equals(templateResults, currentResults)) : ""));
    }

    private static void warmUp() {
        boolean[] results = new boolean[WARMUP_NUMBERS.length];
        long nanoTime = System.nanoTime();

        for (int i = 0; i < WARMUP_NUMBERS.length; i++) {
            results[i] = calculator.isEven(WARMUP_NUMBERS[i]);
        }

        outputLapse("Warmup", "ns", WARMUP_NUMBERS.length, nanoTime, TimeUnit.NANOSECONDS::toNanos, false);
    }

    private static void test(boolean[] templateResults, boolean[] currentResults, boolean outputComparison) {
        long nanoTime = System.nanoTime();

        for (int i = 0; i < NUMBERS.length; i++) {
            currentResults[i] = calculator.isEven(NUMBERS[i]);
        }

        outputLapse("Main", "ns", NUMBERS.length, nanoTime, TimeUnit.NANOSECONDS::toNanos, outputComparison,
                templateResults, currentResults);
    }

    private static int[] readNumbers(String filePath) throws IOException {
        String line = Files.readString(Path.of(filePath));

        String[] numbers = line.split("[^\\d]+");

        int[] result;

        int compensationForLeadingEmpty = numbers[0].isBlank() ? 1 : 0;

        result = new int[numbers.length - compensationForLeadingEmpty];
        IntStream.range(0, numbers.length - compensationForLeadingEmpty)
                .forEach(i -> result[i] = Integer.parseInt(numbers[i + compensationForLeadingEmpty]));

        return result;
    }

    private static boolean isEvenIfElse(int number) {
        if (number == 0) return true;
        else if (number == 1) return false;
        else if (number == 2) return true;
        else if (number == 3) return false;
        else if (number == 4) return true;
        else if (number == 5) return false;
        else if (number == 6) return true;
        else if (number == 7) return false;
        else if (number == 8) return true;
        else if (number == 9) return false;
        else if (number == 10) return true;
        else if (number == 11) return false;
        else if (number == 12) return true;
        else if (number == 13) return false;
        else if (number == 14) return true;
        else throw new IllegalArgumentException(EXCEPTION_TEXT);
    }

    private static boolean isEvenPureIf(int number) {
        if (number == 0) return true;
        if (number == 1) return false;
        if (number == 2) return true;
        if (number == 3) return false;
        if (number == 4) return true;
        if (number == 5) return false;
        if (number == 6) return true;
        if (number == 7) return false;
        if (number == 8) return true;
        if (number == 9) return false;
        if (number == 10) return true;
        if (number == 11) return false;
        if (number == 12) return true;
        if (number == 13) return false;
        if (number == 14) return true;
        throw new IllegalArgumentException(EXCEPTION_TEXT);
    }

    private static boolean isEvenSwitch(int number) {
        switch (number) {
            case 0:
                return true;
            case 2:
                return true;
            case 4:
                return true;
            case 6:
                return true;
            case 8:
                return true;
            case 10:
                return true;
            case 12:
                return true;
            case 14:
                return true;
            case 1:
                return false;
            case 3:
                return false;
            case 5:
                return false;
            case 7:
                return false;
            case 9:
                return false;
            case 11:
                return false;
            case 13:
                return false;
            default:
                throw new IllegalArgumentException(EXCEPTION_TEXT);
        }
    }

    private static boolean isEvenSwitchOptimized(int number) {
        switch (number) {
            case 0:
            case 2:
            case 4:
            case 6:
            case 8:
            case 10:
            case 12:
            case 14:
                return true;
            case 1:
            case 3:
            case 5:
            case 7:
            case 9:
            case 11:
            case 13:
                return false;
            default:
                throw new IllegalArgumentException(EXCEPTION_TEXT);
        }
    }

    private static boolean isEvenOptimized(int number) {
        return (number & 1) == 0;
    }

    private static boolean isEvenSimple(int number) {
        return number % 2 == 0;
    }
}
