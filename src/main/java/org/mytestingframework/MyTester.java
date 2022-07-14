package org.mytestingframework;

import org.mytestingframework.annotations.*;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

import static org.mytestingframework.constant.Color.*;
import static org.mytestingframework.constant.Color.ANSI_RESET;

class MyTester {

    private MyTester() {
    }

    private static Set<Class<?>> classes;

    private static HashSet<MyTestInformation> testResults;

    private static StringBuilder errors;

    static void run(String... testDirectories) {
        classes.forEach(MyTester::testClass);
    }

    static void setup(String... testDirectories) {
        classes = new HashSet<>();
        testResults = new HashSet<>();
        errors = new StringBuilder();

        if (testDirectories.length < 1) {
            errors.append("        No directories to tests. Please choose one or few with test classes.");
        } else {
            for (String directory : testDirectories) {
                Reflections reflections = new Reflections(directory);
                classes.addAll(reflections.getTypesAnnotatedWith(MyTesterTarget.class));
            }

            if (classes.size() < 1) {
                errors.append("        No classes to tests. Please check your test directories.");
            } else {
                errorCheckingInClasses();
            }
        }
    }

    static int getClassesCount() {
        return classes.size();
    }

    static ArrayList<String> getClassesNames() {
        ArrayList<String> names = new ArrayList<>();
        classes.forEach(testClass -> names.add(testClass.getName()));
        return names;
    }

    static String getErrorsString() {
        String errorsString = errors.toString();
        if(errorsString.length() == 0) {
            return null;
        } else if (errorsString.charAt(errorsString.length() - 1) == '\n') {
            return errorsString.substring(0, errorsString.length() - 1) + ANSI_RESET;
        } else {
            return errorsString + ANSI_RESET;
        }
    }

    static HashSet<MyTestInformation> getTestResults() {
        return testResults;
    }

    private static void errorCheckingInClasses() {

        classes.forEach(testClass -> {
            Set<Method> methods = new HashSet<>();
            Collections.addAll(methods, testClass.getMethods());
            int beforeTestCount = 0;
            ArrayList<String> beforeTestMethods = new ArrayList<>();
            int afterTestCount = 0;
            ArrayList<String> afterTestMethods = new ArrayList<>();
            int beforeAllTestsCount = 0;
            ArrayList<String> beforeAllTestsMethods = new ArrayList<>();
            int afterAllTestsCount = 0;
            ArrayList<String> afterAllTestsMethods = new ArrayList<>();
            int testsCount = 0;
            for (Method method : methods) {
                if (method.isAnnotationPresent(MyBeforeTest.class)) {
                    beforeTestCount++;
                    beforeTestMethods.add(method.getName());
                }
                if (method.isAnnotationPresent(MyAfterTest.class)) {
                    afterTestCount++;
                    afterTestMethods.add(method.getName());
                }
                if (method.isAnnotationPresent(MyBeforeAllTests.class)) {
                    beforeAllTestsCount++;
                    beforeAllTestsMethods.add(method.getName());
                }
                if (method.isAnnotationPresent(MyAfterAllTests.class)) {
                    afterAllTestsCount++;
                    afterAllTestsMethods.add(method.getName());
                }
                if (method.isAnnotationPresent(MyTest.class)) {
                    testsCount++;
                    if (method.getReturnType() != void.class) {
                        addModifierError(testClass, method, "not void return type.");
                    }
                    if (!Modifier.isStatic(method.getModifiers())) {
                        addModifierError(testClass, method, "missing static modifier.");
                    }
                }
            }
            if(testsCount == 0) {
                errors.append("        " + ANSI_CYAN).append(testClass.getName()).append(ANSI_RESET + " - no tests in tested class.").append('\n');
            }
            if (beforeTestCount > 1) {
                addBeforeAfterError(beforeTestCount, testClass, beforeTestMethods, "multiple before test annotations.");
            }
            if(afterTestCount > 1) {
                addBeforeAfterError(afterTestCount, testClass, afterTestMethods,"multiple after test annotations.");
            }
            if(beforeAllTestsCount > 1) {
                addBeforeAfterError(beforeAllTestsCount, testClass, beforeAllTestsMethods, "multiple before all tests annotations.");
            }
            if(afterAllTestsCount > 1) {
                addBeforeAfterError(afterAllTestsCount, testClass, afterAllTestsMethods, "multiple after all tests annotations.");
            }
        });
    }

    private static void addBeforeAfterError(int counter, Class<?> testClass, ArrayList<String> methodNames, String message) {
        errors.append("        " + ANSI_CYAN).append(testClass.getName()).append(ANSI_RESET + " - ")
                .append(message).append('\n').append(ANSI_BLUE);
        for (int i = 0; i < counter; ++i) {
            errors.append("            ").append(methodNames.get(i));
            errors.append('\n');
        }
    }

    private static void addModifierError(Class<?> testClass, Method testMethod, String message) {
        errors.append("        ").append(ANSI_CYAN).append(testClass.getName()).append(ANSI_RESET).append(" : ")
                .append(ANSI_BLUE).append(testMethod.getName()).append(ANSI_RESET + " - ").append(message).append('\n');
    }

    private static void testClass(Class<?> testedClass) {
        Set<Method> tests = findTests(testedClass);
        Method beforeAllTestsMethod = findBeforeAllTestsMethod(testedClass);
        Method afterAllTestsMethod = findAfterAllTestsMethod(testedClass);
        Method beforeTestMethod = findBeforeTestMethod(testedClass);
        Method afterTestMethod = findAfterTestMethod(testedClass);

        if (beforeAllTestsMethod != null) {
            try {
                beforeAllTestsMethod.invoke(testedClass);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        tests.forEach(method -> testMethod(testedClass, method, beforeTestMethod, afterTestMethod));

        if (afterAllTestsMethod != null) {
            try {
                afterAllTestsMethod.invoke(testedClass);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    private static Method findBeforeTestMethod(Class<?> testedClass) {
        for (Method method : testedClass.getMethods()) {
            if (method.isAnnotationPresent(MyBeforeTest.class)) {
                return method;
            }
        }
        return null;
    }

    private static Method findAfterTestMethod(Class<?> testedClass) {
        for (Method method : testedClass.getMethods()) {
            if (method.isAnnotationPresent(MyAfterTest.class)) {
                return method;
            }
        }
        return null;
    }

    private static Method findBeforeAllTestsMethod(Class<?> testedClass) {
        for (Method method : testedClass.getMethods()) {
            if (method.isAnnotationPresent(MyBeforeAllTests.class)) {
                return method;
            }
        }
        return null;
    }

    private static Method findAfterAllTestsMethod(Class<?> testedClass) {
        for (Method method : testedClass.getMethods()) {
            if (method.isAnnotationPresent(MyAfterAllTests.class)) {
                return method;
            }
        }
        return null;
    }

    private static Set<Method> findTests(Class<?> testedClass) {
        Set<Method> methods = new HashSet<>();
        Set<Method> tests = new HashSet<>();
        Collections.addAll(methods, testedClass.getMethods());

        methods.forEach(method -> {
            if (method.isAnnotationPresent(MyTest.class)) {
                tests.add(method);
            }
        });
        return tests;
    }

    private static void testMethod(Class<?> testedClass, Method testedMethod,
                                   Method beforeTestMethod, Method afterTestMethod) {
        if (beforeTestMethod != null) {
            try {
                beforeTestMethod.invoke(testedClass);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        long time = System.currentTimeMillis();
        String error = null;
        try {
            MyTestPrinter.printTestName(testedClass, testedMethod);
            testedMethod.invoke(testedClass);
        } catch (InvocationTargetException e) {
            error = e.getCause().getMessage();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        time = System.currentTimeMillis() - time;

        if (afterTestMethod != null) {
            try {
                afterTestMethod.invoke(testedClass);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        boolean success = Objects.isNull(error);
        testResults.add(new MyTestInformation(testedClass, testedMethod, success, (double) time / 1000, error));
    }

}
