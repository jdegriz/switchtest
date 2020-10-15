package com.kakhanov;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;

public class ReflectionUtil {
    public static Method getEvenCalculatorMethodByName(String methodName) throws NoSuchMethodException {
        return MathUtil.class.getDeclaredMethod(methodName, int.class);
    }

    public static MethodHandle getEvenCalculatorMethodHandleByName(String methodName) throws NoSuchMethodException, IllegalAccessException {
        MethodHandles.Lookup lookup = MethodHandles.lookup();

        MethodType mt = MethodType.methodType(boolean.class, int.class);
        return lookup.findStatic(MathUtil.class, methodName, mt);
    }
}