package org.mytestingframework;

import java.lang.reflect.Method;
import java.util.HashSet;

/**
 * DTO class for storage information about tests.
 */
class MyTestInformation {
    private Class<?> testingClass;
    private Method testingMethod;
    private boolean result;
    private double timeInSeconds;
    private String error;

    public MyTestInformation(Class<?> testingClass, Method testingMethod, boolean result, double timeInSeconds, String error) {
        this.testingClass = testingClass;
        this.testingMethod = testingMethod;
        this.result = result;
        this.timeInSeconds = timeInSeconds;
        this.error = error;
    }

    public Class<?> getTestingClass() {
        return testingClass;
    }

    public void setTestingClass(Class<?> testingClass) {
        this.testingClass = testingClass;
    }

    public Method getTestingMethod() {
        return testingMethod;
    }

    public void setTestingMethod(Method testingMethod) {
        this.testingMethod = testingMethod;
    }

    public boolean getResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public double getTimeInSeconds() {
        return timeInSeconds;
    }

    public void setTimeInSeconds(double timeInSeconds) {
        this.timeInSeconds = timeInSeconds;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
