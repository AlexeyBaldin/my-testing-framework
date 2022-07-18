package org.mytestingframework;

import org.mytestingframework.throwable.MyTestingFrameworkException;

/**
 * Class that is running testing process.
 * <p>
 * Class used to start testing.
 */
public class MyTestingFramework {

    private MyTestingFramework() {}

    /**
     * Method used to start testing.
     * @param testDirectories - Array with strings that represent path to package where framework will be found classes with tests
     * @throws MyTestingFrameworkException - thrown when there are incorrectly written tests or tested classes.
     */
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
