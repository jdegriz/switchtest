package com.kakhanov;

import java.io.IOException;
import java.lang.invoke.MethodHandle;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static com.kakhanov.FileUtil.readNumbers;
import static com.kakhanov.LogUtil.outputLapse;
import static com.kakhanov.ReflectionUtil.getEvenCalculatorMethodByName;
import static com.kakhanov.ReflectionUtil.getEvenCalculatorMethodHandleByName;

public class MainResultsOmittedMethodHandle {
    private static int[] WARMUP_NUMBERS;
    private static int[] NUMBERS;

    private static MethodHandle calc = null;

    public static void main(String[] args) throws Throwable {
        System.out.println("Test name: " + MainResultsOmittedMethodHandle.class.getSimpleName());
        System.out.println("Method name: " + args[0]);
        calc = getEvenCalculatorMethodHandleByName(args[0]);

        WARMUP_NUMBERS = readNumbers(Constants.WARMUP_NUMBERS_FILE_PATH);
        NUMBERS = readNumbers(Constants.NUMBERS_FILE_PATH);

        warmUp();

        System.out.println();

        IntStream.range(0, Constants.NUMBER_OF_TEST_RUNS).forEach(i -> {
            try {
                test();
            } catch (Throwable e) {
                e.printStackTrace(System.err);
            }
        });
    }

    private static void warmUp() throws Throwable {
        long nanoTime = System.nanoTime();
        boolean temp = false;

        for (int i = 0; i < WARMUP_NUMBERS.length; i++) {
            temp = (boolean) calc.invokeExact(WARMUP_NUMBERS[i]);
        }

        outputLapse("Warmup", "ns", WARMUP_NUMBERS.length, nanoTime, TimeUnit.NANOSECONDS::toNanos);
    }

    private static void test() throws Throwable {
        long nanoTime = System.nanoTime();
        boolean temp = false;

        for (int i = 0; i < NUMBERS.length; i++) {
            temp = (boolean) calc.invokeExact(NUMBERS[i]);
        }

        outputLapse("Main", "ns", NUMBERS.length, nanoTime, TimeUnit.NANOSECONDS::toNanos);
    }
}
