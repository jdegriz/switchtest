package com.kakhanov;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static com.kakhanov.FileUtil.readNumbers;
import static com.kakhanov.LogUtil.outputLapse;
import static com.kakhanov.ReflectionUtil.getEvenCalculatorMethodByName;

public class MainResultsOmittedReflection {
    private static int[] WARMUP_NUMBERS;
    private static int[] NUMBERS;

    private static Method calc = null;

    public static void main(String[] args) throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        System.out.println("Method name: " + args[0]);
        calc = getEvenCalculatorMethodByName(args[0]);

        WARMUP_NUMBERS = readNumbers(Constants.WARMUP_NUMBERS_FILE_PATH);
        NUMBERS = readNumbers(Constants.NUMBERS_FILE_PATH);

        warmUp();

        System.out.println();

        IntStream.range(0, Constants.NUMBER_OF_TEST_RUNS).forEach(i -> {
            try {
                test();
            } catch (InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace(System.err);
            }
        });
    }

    private static void warmUp() throws InvocationTargetException, IllegalAccessException {
        long nanoTime = System.nanoTime();

        for (int i = 0; i < WARMUP_NUMBERS.length; i++) {
            calc.invoke(MainResultsOmittedReflection.class, WARMUP_NUMBERS[i]);
        }

        outputLapse("Warmup", "ns", WARMUP_NUMBERS.length, nanoTime, TimeUnit.NANOSECONDS::toNanos);
    }

    private static void test() throws InvocationTargetException, IllegalAccessException {
        long nanoTime = System.nanoTime();

        for (int i = 0; i < NUMBERS.length; i++) {
            calc.invoke(MainResultsOmittedReflection.class, NUMBERS[i]);
        }

        outputLapse("Main", "ns", NUMBERS.length, nanoTime, TimeUnit.NANOSECONDS::toNanos);
    }
}
