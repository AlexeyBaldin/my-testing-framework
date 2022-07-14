package org.mytestingframework.asserts;

import org.mytestingframework.throwable.MyAssertError;

import java.util.Objects;

public final class MyAssert {
    private MyAssert() {}

    private static void fail(String message) {
        throw new MyAssertError(message);
    }

    private static void failNull() {
        fail("Expected not null object, but actual is null");
    }

    private static void failNotNull() {
        fail("Expected null object, but actual is not null");
    }

    private static void failEquals(Object expected, Object actual) {
        fail("Expected object: " + expected + " | " + "Actual object: " + actual);
    }

    private static void failNotEquals(Object unexpected) {
        fail("Object shouldn`t be: " + unexpected + ", but it is");
    }

    public static void assertNull(Object actual) {
        if(actual != null) {
            failNotNull();
        }
    }

    public static void assertNotNull(Object actual) {
        if(actual == null) {
            failNull();
        }
    }

    public static void assertFail() {
        fail("Assert fail");
    }

    public static void assertFail(String message) {
        fail(message);
    }

    public static void assertTrue(boolean condition) {
        if(!condition) {
            failEquals(true, condition);
        }
    }

    public static void assertFalse(boolean condition) {
        if(condition) {
            failEquals(false, condition);
        }
    }

    public static void assertEquals(long expected, long actual) {
        if(expected != actual) {
            failEquals(expected, actual);
        }
    }

    public static void assertEquals(double expected, double actual) {
        if(expected != actual) {
            failEquals(expected, actual);
        }
    }

    public static void assertEquals(Object expected, Object actual) {
        if(!Objects.equals(expected, actual)) {
            failEquals(expected, actual);
        }
    }

    public static void assertNotEquals(long unexpected, long actual) {
        if(unexpected == actual) {
            failNotEquals(unexpected);
        }
    }

    public static void assertNotEquals(double unexpected, double actual) {
        if(unexpected == actual) {
            failNotEquals(unexpected);
        }
    }

    public static void assertNotEquals(Object unexpected, Object actual) {
        if(Objects.equals(unexpected, actual)) {
            failNotEquals(unexpected);
        }
    }

}
