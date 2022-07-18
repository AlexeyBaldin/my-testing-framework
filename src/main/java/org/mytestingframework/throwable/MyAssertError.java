package org.mytestingframework.throwable;

/**
 * Error that is thrown when assertions failed.
 */
public class MyAssertError extends Error {
    public MyAssertError() {
        super();
    }

    public MyAssertError(String message) {
        super(message);
    }
}
