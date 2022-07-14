package org.mytestingframework;

import org.mytestingframework.MyTestingFramework;
import org.mytestingframework.annotations.*;
import org.mytestingframework.asserts.MyAssert;
import org.mytestingframework.constant.About;
import org.mytestingframework.throwable.MyAssertError;

import static org.mytestingframework.constant.Color.*;

@MyTesterTarget
public class Main {
    public static void main(String[] args) {
        try {
            MyTestingFramework.run("");


        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @MyTest
    public static void test() {
        MyAssert.assertTrue(true);
    }

//    @MyTest
//    public static int testSuccess() {
//        System.out.println("Test success");
//        MyAssert.assertTrue(true);
//        return 1;
//    }
//    @MyTest
//    public void testError() {
//        System.out.println("Test error");
//        MyAssert.assertFalse(true);
//    }
//
//    @MyBeforeTest
//    public static void a() {}
//
//    @MyBeforeTest
//    public static void b() {}
//
//    @MyAfterTest
//    public static void c() {}
//
//    @MyAfterTest
//    public static void d() {}
//
//    @MyBeforeAllTests
//    public static void e() {}
//    @MyBeforeAllTests
//    public static void f() {}
//    @MyAfterAllTests
//    public static void g() {}
//    @MyAfterAllTests
//    public static void h() {}

}
