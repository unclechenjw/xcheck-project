package com.github.xcheck.core.util;

import java.util.Collection;

public abstract class Assert {
    
    public static void simpleExpressionIllegal(String expression) {
        int atIndex = expression.indexOf("@");
        int numberSignIndex = expression.indexOf("#");
        if (atIndex != -1 && numberSignIndex != -1) {
            throw new IllegalArgumentException(
                    "what the hell is @ and # together?");
        }
    }

    public static void isTrue(boolean expression, String message) {
        if (!(expression))
            throw new IllegalArgumentException(message);
    }

    public static void isTrue(boolean expression) {
        isTrue(expression, "[Assertion failed] - this expression must be true");
    }

    public static void isNull(Object object, String message) {
        if (object != null)
            throw new IllegalArgumentException(message);
    }

    public static void isNull(Object object) {
        isNull(object, "[Assertion failed] - the object argument must be null");
    }

    public static void notNull(Object object, String message) {
        if (object == null)
            throw new IllegalArgumentException(message);
    }

    public static void notNull(Object object) {
        notNull(object, "[Assertion failed] - this argument is required; it must not be null");
    }

    public static void notEmpty(Object[] array, String message) {
        if (array == null || array.length == 0)
            throw new IllegalArgumentException(message);
    }

    public static void notEmpty(Object[] array) {
        notEmpty(array, "[Assertion failed] - this array must not be empty: it must contain at least 1 element");
    }

    public static void noNullElements(Object[] array, String message) {
        if (array != null)
            for (Object element : array)
                if (element == null)
                    throw new IllegalArgumentException(message);
    }

    public static void noNullElements(Object[] array) {
        noNullElements(array, "[Assertion failed] - this array must not contain any null elements");
    }

    public static void notEmpty(Collection<?> collection, String message) {
        if (collection == null || collection.size() == 0)
            throw new IllegalArgumentException(message);
    }

}