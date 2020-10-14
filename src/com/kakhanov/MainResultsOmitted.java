package com.kakhanov;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.IntStream;

import static com.kakhanov.Contants.EXCEPTION_TEXT;

public class MainResultsOmitted {
    private static int[] WARMUP_NUMBERS;
    private static int[] NUMBERS;

    private static EvenCalculator calculator = MainResultsOmitted::isEvenOptimized;
//    private static EvenCalculator calculator = MainResultsOmitted::isEvenSimple;
//    private static EvenCalculator calculator = MainResultsOmitted::isEvenSwitch;
//    private static EvenCalculator calculator = MainResultsOmitted::isEvenSwitchOptimized;
//    private static EvenCalculator calculator = MainResultsOmitted::isEvenIfElse;
//    private static EvenCalculator calculator = MainResultsOmitted::isEvenPureIf;

    public static void main(String[] args) throws IOException {
        WARMUP_NUMBERS = readNumbers(Contants.WARMUP_NUMBERS_FILE_PATH);
        NUMBERS = readNumbers(Contants.NUMBERS_FILE_PATH);

        warmUp();

        System.out.println("");

        IntStream.range(0, Contants.NUMBER_OF_TEST_RUNS).forEach(i -> test());
    }

    private static void outputLapse(String name, String timeText, int count, long nanoTime,
                                    Function<Long, Long> timeConverter) {
        long nanosBeforeStringConcat = System.nanoTime();

        System.out.println(name + " count: " + count + "; " + name.toLowerCase() +
                " time (" + timeText + "): " +
                timeConverter.apply(nanosBeforeStringConcat - nanoTime));
    }

    private static void warmUp() {
        long nanoTime = System.nanoTime();

        for (int i = 0; i < WARMUP_NUMBERS.length; i++) {
            calculator.isEven(WARMUP_NUMBERS[i]);
        }

        outputLapse("Warmup", "ns", WARMUP_NUMBERS.length, nanoTime, TimeUnit.NANOSECONDS::toNanos);
    }

    private static void test() {
        long nanoTime = System.nanoTime();

        for (int i = 0; i < NUMBERS.length; i++) {
            calculator.isEven(NUMBERS[i]);
        }

        outputLapse("Main", "ns", NUMBERS.length, nanoTime, TimeUnit.NANOSECONDS::toNanos);
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
