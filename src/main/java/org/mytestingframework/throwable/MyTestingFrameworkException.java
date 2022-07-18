package org.mytestingframework.throwable;

/**
 * Exception that is thrown when there are incorrectly written tests or tested classes.
 */
public class MyTestingFrameworkException extends Exception {
    public MyTestingFrameworkException() {
        super();
    }

    public MyTestingFrameworkException(String message) {
        super(message);
    }
}
