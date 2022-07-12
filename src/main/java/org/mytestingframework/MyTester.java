package org.mytestingframework;

import org.mytestingframework.annotations.*;
import org.mytestingframework.constant.Color;
import org.mytestingframework.throwable.MyAssertError;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

class MyTester {

    private MyTester() {}

    private static Set<Class<?>> classes;

    private static HashSet<MyTestInformation> testResults;

    private static StringBuilder errorClasses;

    static void run(String... testDirectories) {
        classes.forEach(MyTester::testClass);
    }

    static void setup(String... testDirectories) {
        classes = new HashSet<>();
        testResults = new HashSet<>();
        errorClasses = new StringBuilder();


        for (String directory : testDirectories) {
            Reflections reflections = new Reflections(directory);
            classes.addAll(reflections.getTypesAnnotatedWith(MyTesterTarget.class));
        }

        checkBeforeAndAfterAnnotationsAndFindDefectiveClasses();
    }

    static int getClassesCount() {
        return classes.size();
    }

    static ArrayList<String> getClassesNames() {
        ArrayList<String> names = new ArrayList<>();
        classes.forEach(testClass -> names.add(testClass.getName()));
        return names;
    }

    static String getErrorClassesString() {
        return errorClasses.toString();
    }

    static HashSet<MyTestInformation> getTestResults() {
        return testResults;
    }

    private static void checkBeforeAndAfterAnnotationsAndFindDefectiveClasses() {

        classes.forEach(testClass -> {
            Set<Method> methods = new HashSet<>();
            Collections.addAll(methods, testClass.getMethods());
            AtomicInteger beforeTestCount = new AtomicInteger();
            AtomicInteger afterTestCount = new AtomicInteger();
            AtomicInteger beforeAllTestsCount = new AtomicInteger();
            AtomicInteger afterAllTestsCount = new AtomicInteger();
            methods.forEach(method -> {
                if (method.isAnnotationPresent(MyBeforeTest.class)) {
                    beforeTestCount.getAndIncrement();
                }
                if (method.isAnnotationPresent(MyAfterTest.class)) {
                    afterTestCount.getAndIncrement();
                }
                if (method.isAnnotationPresent(MyBeforeAllTests.class)) {
                    beforeAllTestsCount.getAndIncrement();
                }
                if (method.isAnnotationPresent(MyAfterAllTests.class)) {
                    afterAllTestsCount.getAndIncrement();
                }
            });
            if (beforeTestCount.get() > 1 || afterTestCount.get() > 1 ||
                    beforeAllTestsCount.get() > 1 || afterAllTestsCount.get() > 1) {
                errorClasses.append(testClass.getName()).append(" - multiple before/after annotations. ");
            }
        });
    }

    private static void testClass(Class<?> testedClass) {
        Set<Method> tests = findTests(testedClass);
        Method beforeAllTestsMethod = findBeforeAllTestsMethod(testedClass);
        Method afterAllTestsMethod = findAfterAllTestsMethod(testedClass);
        Method beforeTestMethod = findBeforeTestMethod(testedClass);
        Method afterTestMethod = findAfterTestMethod(testedClass);

        if(beforeAllTestsMethod != null) {
            try {
                beforeAllTestsMethod.invoke(testedClass);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        tests.forEach(method -> testMethod(testedClass, method, beforeTestMethod, afterTestMethod));

        if(afterAllTestsMethod != null) {
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
            if (method.isAnnotationPresent(MyTest.class) &&
                    method.getReturnType() == void.class &&
                    Modifier.isStatic(method.getModifiers())) {
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
        testResults.add(new MyTestInformation(testedClass, testedMethod, success, (double)time / 1000, error));
    }

}
