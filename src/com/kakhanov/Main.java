package com.kakhanov;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static com.kakhanov.DataUtil.clearResults;
import static com.kakhanov.FileUtil.readNumbers;
import static com.kakhanov.LogUtil.outputLapse;

public class Main {
    private static int[] WARMUP_NUMBERS;
    private static int[] NUMBERS;

    private static EvenCalculator calculator = MathUtil::isEvenOptimized;
//    private static EvenCalculator calculator = MathUtil::isEvenSimple;
//    private static EvenCalculator calculator = MathUtil::isEvenSwitch;
//    private static EvenCalculator calculator = MathUtil::isEvenSwitchOptimized;
//    private static EvenCalculator calculator = MathUtil::isEvenIfElse;
//    private static EvenCalculator calculator = MathUtil::isEvenPureIf;

    private static boolean[] firstResults;
    private static boolean[] otherResults;

    public static void main(String[] args) throws IOException {
        System.out.println("Test name: " + Main.class.getSimpleName());

        WARMUP_NUMBERS = readNumbers(Constants.WARMUP_NUMBERS_FILE_PATH);
        NUMBERS = readNumbers(Constants.NUMBERS_FILE_PATH);
        firstResults = new boolean[NUMBERS.length];
        otherResults = new boolean[NUMBERS.length];

        clearResults(firstResults);
        warmUp();

        System.out.println();

        IntStream.range(0, Constants.NUMBER_OF_TEST_RUNS)
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
}
