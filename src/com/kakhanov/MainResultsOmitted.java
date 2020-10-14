package com.kakhanov;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static com.kakhanov.FileUtil.readNumbers;
import static com.kakhanov.LogUtil.outputLapse;

public class MainResultsOmitted {
    private static int[] WARMUP_NUMBERS;
    private static int[] NUMBERS;

    private static EvenCalculator calculator = MathUtil::isEvenOptimized;
//    private static EvenCalculator calculator = MathUtil::isEvenSimple;
//    private static EvenCalculator calculator = MathUtil::isEvenSwitch;
//    private static EvenCalculator calculator = MathUtil::isEvenSwitchOptimized;
//    private static EvenCalculator calculator = MathUtil::isEvenIfElse;
//    private static EvenCalculator calculator = MathUtil::isEvenPureIf;

    public static void main(String[] args) throws IOException {
        WARMUP_NUMBERS = readNumbers(Constants.WARMUP_NUMBERS_FILE_PATH);
        NUMBERS = readNumbers(Constants.NUMBERS_FILE_PATH);

        warmUp();

        System.out.println("");

        IntStream.range(0, Constants.NUMBER_OF_TEST_RUNS).forEach(i -> test());
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
}
