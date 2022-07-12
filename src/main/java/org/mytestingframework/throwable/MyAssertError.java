package org.mytestingframework.throwable;

public class MyAssertError extends Error {
    public MyAssertError() {
        super();
    }

    public MyAssertError(String message) {
        super(message);
    }
}
