package com.kakhanov;

import static com.kakhanov.Constants.EXCEPTION_TEXT;

public class MathUtil {
    public static boolean isEvenIfElse(int number) {
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

    public static boolean isEvenPureIf(int number) {
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

    public static boolean isEvenSwitch(int number) {
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

    public static boolean isEvenSwitchOptimized(int number) {
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

    public static boolean isEvenOptimized(int number) {
        return (number & 1) == 0;
    }

    public static boolean isEvenSimple(int number) {
        return number % 2 == 0;
    }
}
