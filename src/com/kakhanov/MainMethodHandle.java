package com.kakhanov;

import java.lang.invoke.MethodHandle;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static com.kakhanov.DataUtil.clearResults;
import static com.kakhanov.FileUtil.readNumbers;
import static com.kakhanov.LogUtil.outputLapse;
import static com.kakhanov.ReflectionUtil.getEvenCalculatorMethodHandleByName;

public class MainMethodHandle {
    private static int[] WARMUP_NUMBERS;
    private static int[] NUMBERS;

    private static MethodHandle calc = null;

    private static boolean[] firstResults;
    private static boolean[] otherResults;

    public static void main(String[] args) throws Throwable {
        System.out.println("Test name: " + MainMethodHandle.class.getSimpleName());
        System.out.println("Method name: " + args[0]);
        calc = getEvenCalculatorMethodHandleByName(args[0]);

        WARMUP_NUMBERS = readNumbers(Constants.WARMUP_NUMBERS_FILE_PATH);
        NUMBERS = readNumbers(Constants.NUMBERS_FILE_PATH);
        firstResults = new boolean[NUMBERS.length];
        otherResults = new boolean[NUMBERS.length];

        clearResults(firstResults);
        warmUp();

        System.out.println();

        IntStream.range(0, Constants.NUMBER_OF_TEST_RUNS)
                .forEach(i -> {
                    try {
                        if (i == 0) {
                            clearResults(firstResults);
                            test(null, firstResults, false);
                        } else {
                            clearResults(otherResults);
                            test(firstResults, otherResults, true);
                        }
                    } catch (Throwable e) {
                        e.printStackTrace(System.err);
                    }
                });
    }

    private static void warmUp() throws Throwable {
        boolean[] results = new boolean[WARMUP_NUMBERS.length];
        long nanoTime = System.nanoTime();

        for (int i = 0; i < WARMUP_NUMBERS.length; i++) {
            results[i] = (boolean) calc.invokeExact(WARMUP_NUMBERS[i]);
        }

        outputLapse("Warmup", "ns", WARMUP_NUMBERS.length, nanoTime, TimeUnit.NANOSECONDS::toNanos, false);
    }

    private static void test(boolean[] templateResults, boolean[] currentResults, boolean outputComparison) throws Throwable {
        long nanoTime = System.nanoTime();

        for (int i = 0; i < NUMBERS.length; i++) {
            currentResults[i] = (boolean) calc.invokeExact(NUMBERS[i]);
        }

        outputLapse("Main", "ns", NUMBERS.length, nanoTime, TimeUnit.NANOSECONDS::toNanos, outputComparison,
                templateResults, currentResults);
    }
}
