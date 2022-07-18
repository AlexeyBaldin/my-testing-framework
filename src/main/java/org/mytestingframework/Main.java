package org.mytestingframework;

import org.mytestingframework.MyTestingFramework;
import org.mytestingframework.annotations.*;
import org.mytestingframework.asserts.MyAssert;
import org.mytestingframework.constant.About;
import org.mytestingframework.throwable.MyAssertError;
import org.mytestingframework.throwable.MyTestingFrameworkException;

import static org.mytestingframework.constant.Color.*;

/**
 * This is class for testing functionality of My Testing Framework ;)
 */
@MyTesterTarget
public class Main {
    public static void main(String[] args) {
        try {
            MyTestingFramework.run("org.mytestingframework");
        } catch (MyTestingFrameworkException e) {
            e.printStackTrace();
        }
    }

    @MyTest
    public static void test() {
        System.out.println("qweqweqweqwe");
        MyAssert.assertEquals(1, 15);
    }

    @MyTest
    public static void test1() {
        System.out.println("qweqweqweqwe");
        MyAssert.assertEquals(15, 15);
    }

    @MyBeforeTest
    public static void a() {
        System.out.println("qwe");
    }

}
