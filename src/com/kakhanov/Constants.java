package com.kakhanov;

public class Constants {
    public static int NUMBER_OF_TEST_RUNS = 100;

    public static int NUMBER_OF_ITERATIONS = 100_000_000;
    public static int NUMBER_OF_WARMUP_ITERATIONS = 10_000;
    public static int MAX_NUMBER = 10;

    public static String WARMUP_NUMBERS_FILE_PATH = "resources/example_data/warmup_numbers.txt";
    public static String NUMBERS_FILE_PATH = "resources/example_data/numbers.txt";
    public static String EXCEPTION_TEXT = "number below 0 and above " + MAX_NUMBER + " are not permitted";
}
