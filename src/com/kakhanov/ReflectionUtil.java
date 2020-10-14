package com.kakhanov;

import java.lang.reflect.Method;

public class ReflectionUtil {
    public static Method getEvenCalculatorMethodByName(String methodName) throws NoSuchMethodException {
        return MathUtil.class.getDeclaredMethod(methodName, int.class);
    }
}