package org.mytestingframework;

import org.mytestingframework.constant.About;
import org.mytestingframework.constant.Config;

import java.lang.reflect.Method;
import java.util.*;

import static org.mytestingframework.constant.Color.*;

class MyTestPrinter {

    private MyTestPrinter() {}

    static void hello() {
        StringBuilder stringBuilder = new StringBuilder();
        System.out.println(padAndFillString(""));
        System.out.println(padAndFillString("   ╔╗  ╔╗╔╗╔╗"));
        System.out.println(padAndFillString("   ║║  ║║║║║║"));
        System.out.println(padAndFillString("   ║╚╗╔╝║║╚╝║"));
        System.out.println(padAndFillString("   ║╔╗╔╗║╚═╗║"));
        String appendString = String.format("%" + ((25 - About.VERSION.length())/2 + About.VERSION.length() + 1) + "s", "|" + About.VERSION + "|");
        stringBuilder.append("   ║║╚╝║║ ╔╝║").append(appendString);
        System.out.println(padAndFillString(stringBuilder.toString()));
        System.out.println(padAndFillString("   ╚╝  ╚╝ ╚═╝       " + About.AUTHOR_NAME + About.AUTHOR_SNAME));
        System.out.println(padAndFillString("   ╔════╗╔═══╗╔══╗╔════╗╔══╗╔╗─╔╗╔═══╗"));
        System.out.println(padAndFillString("   ╚═╗╔═╝║╔══╝║╔═╝╚═╗╔═╝╚╗╔╝║╚═╝║║╔══╝"));
        System.out.println(padAndFillString("     ║║  ║╚══╗║╚═╗  ║║   ║║ ║╔╗ ║║║╔═╗"));
        System.out.println(padAndFillString("     ║║  ║╔══╝╚═╗║  ║║   ║║ ║║╚╗║║║╚╗║"));
        System.out.println(padAndFillString("     ║║  ║╚══╗╔═╝║  ║║  ╔╝╚╗║║ ║║║╚═╝║"));
        System.out.println(padAndFillString("     ╚╝  ╚═══╝╚══╝  ╚╝  ╚══╝╚╝ ╚╝╚═══╝"));
        System.out.println(padAndFillString("   ╔══╗╔═══╗╔══╗╔╗  ╔╗╔═══╗╔╗╔╗╔╗╔══╗╔═══╗╔╗╔══╗"));
        System.out.println(padAndFillString("   ║╔═╝║╔═╗║║╔╗║║║  ║║║╔══╝║║║║║║║╔╗║║╔═╗║║║║╔═╝"));
        System.out.println(padAndFillString("   ║╚═╗║╚═╝║║╚╝║║╚╗╔╝║║╚══╗║║║║║║║║║║║╚═╝║║╚╝║"));
        System.out.println(padAndFillString("   ║╔═╝║╔╗╔╝║╔╗║║╔╗╔╗║║╔══╝║║║║║║║║║║║╔╗╔╝║╔╗║"));
        System.out.println(padAndFillString("   ║║  ║║║║ ║║║║║║╚╝║║║╚══╗║╚╝╚╝║║╚╝║║║║║ ║║║╚═╗"));
        System.out.println(padAndFillString("   ╚╝  ╚╝╚╝ ╚╝╚╝╚╝  ╚╝╚═══╝╚═╝╚═╝╚══╝╚╝╚╝ ╚╝╚══╝"));
        System.out.println(padAndFillString(""));
        System.out.println(padAndFillString("   Start testing."));
        System.out.println(padAndFillString("   Start internal output..."));
    }

    private static String padAndFillString(String string) {
        return String.format("%-" + Config.PRINTER_STRING_SIZE + "s", string).replace(" ", Config.PRINTER_FILLER);
    }

    static void goodbye() {
        System.out.println(padAndFillString("   Finish testing."));
        System.out.println(padAndFillString(""));
   }

    static void printTestResults(HashSet<MyTestInformation> testResults) {
        System.out.println(padAndFillString("   End internal output."));
        System.out.println(padAndFillString("   Results:"));
        System.out.println(formTestResultsString(testResults));
    }

    static void printTestName(Class<?> testedClass, Method testedMethod) {
        System.out.println("    " + ANSI_CYAN + testedClass.getName() + ANSI_RESET + " : " + ANSI_BLUE + testedMethod.getName() + ANSI_RESET);
    }

    private static StringBuilder formTestResultsString(HashSet<MyTestInformation> testResults) {
        StringBuilder stringBuilder = new StringBuilder();

        ArrayList<String> testNames = new ArrayList<>();
        testResults.forEach(testResult -> testNames.add(testResult.getTestingMethod().getName()));

        int maxClassNameLength = Collections.max(MyTester.getClassesNames(), Comparator.comparing(String::length)).length();
        int maxTestNameLength = Collections.max(testNames, Comparator.comparing(String::length)).length();

        stringBuilder.append('\n');
        testResults.forEach(testResult -> {
            stringBuilder.append("   [" + ANSI_CYAN).append(String.format("%-" + maxClassNameLength + "s", testResult.getTestingClass().getName()))
                    .append(ANSI_RESET).append(" : ")
                    .append(ANSI_BLUE).append(String.format("%-" + maxTestNameLength + "s", testResult.getTestingMethod().getName()))
                    .append(ANSI_RESET).append("]");

            if (testResult.getResult()) {
                stringBuilder.append(ANSI_GREEN + "      Test Success  :)   " + ANSI_YELLOW).append(String.format("%10s", testResult.getTimeInSeconds())).append(" sec.").append(ANSI_RESET).append('\n');
            } else {
                stringBuilder.append(ANSI_RED + "        Test Error  :(   " + ANSI_YELLOW).append(String.format("%10s", testResult.getTimeInSeconds())).append(" sec.").append(ANSI_RESET).append('\n');
            }
        });

        return stringBuilder;
    }
}
