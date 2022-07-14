package org.mytestingframework;

import org.mytestingframework.throwable.MyTestingFrameworkException;

public class MyTestingFramework {

    private MyTestingFramework() {}

    public static void run(String... testDirectories) throws MyTestingFrameworkException {
        MyTester.setup(testDirectories);

        if(MyTester.getErrorsString() != null) {
            throw new MyTestingFrameworkException("Please fix following errors: \n" + MyTester.getErrorsString());
        }

        MyTestPrinter.hello();
        MyTester.run(testDirectories);
        MyTestPrinter.printTestResults(MyTester.getTestResults());
        MyTestPrinter.goodbye();
    }
}
