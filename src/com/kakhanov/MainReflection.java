package com.kakhanov;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static com.kakhanov.DataUtil.clearResults;
import static com.kakhanov.FileUtil.readNumbers;
import static com.kakhanov.LogUtil.outputLapse;
import static com.kakhanov.ReflectionUtil.getEvenCalculatorMethodByName;

public class MainReflection {
    private static int[] WARMUP_NUMBERS;
    private static int[] NUMBERS;

    private static Method calc = null;

    private static boolean[] firstResults;
    private static boolean[] otherResults;

    public static void main(String[] args) throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        System.out.println("Method name: " + args[0]);
        calc = getEvenCalculatorMethodByName(args[0]);

        WARMUP_NUMBERS = readNumbers(Constants.WARMUP_NUMBERS_FILE_PATH);
        NUMBERS = readNumbers(Constants.NUMBERS_FILE_PATH);
        firstResults = new boolean[NUMBERS.length];
        otherResults = new boolean[NUMBERS.length];

        clearResults(firstResults);
        warmUp();

        System.out.println("");

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
                    } catch (InvocationTargetException | IllegalAccessException e) {
                        e.printStackTrace(System.err);
                    }
                });
    }

    private static void warmUp() throws InvocationTargetException, IllegalAccessException {
        boolean[] results = new boolean[WARMUP_NUMBERS.length];
        long nanoTime = System.nanoTime();

        for (int i = 0; i < WARMUP_NUMBERS.length; i++) {
            results[i] = (boolean) calc.invoke(MainResultsOmittedReflection.class, WARMUP_NUMBERS[i]);
        }

        outputLapse("Warmup", "ns", WARMUP_NUMBERS.length, nanoTime, TimeUnit.NANOSECONDS::toNanos, false);
    }

    private static void test(boolean[] templateResults, boolean[] currentResults, boolean outputComparison) throws InvocationTargetException, IllegalAccessException {
        long nanoTime = System.nanoTime();

        for (int i = 0; i < NUMBERS.length; i++) {
            currentResults[i] = (boolean) calc.invoke(MainResultsOmittedReflection.class, NUMBERS[i]);
        }

        outputLapse("Main", "ns", NUMBERS.length, nanoTime, TimeUnit.NANOSECONDS::toNanos, outputComparison,
                templateResults, currentResults);
    }
}
