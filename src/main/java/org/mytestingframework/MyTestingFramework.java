package org.mytestingframework;

public class MyTestingFramework {

    private MyTestingFramework() {}

    public static void run(String... testDirectories) throws Exception {
        MyTester.setup(testDirectories);

        if(MyTester.getErrorsString().length() != 0) {
            throw new Exception("Please fix following errors: \n" + MyTester.getErrorsString());
        }

        MyTestPrinter.hello();
        MyTester.run(testDirectories);
        MyTestPrinter.printTestResults(MyTester.getTestResults());
        MyTestPrinter.goodbye();
    }
}
