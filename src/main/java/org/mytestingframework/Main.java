package org.mytestingframework;

import org.mytestingframework.MyTestingFramework;
import org.mytestingframework.annotations.MyTest;
import org.mytestingframework.annotations.MyTesterTarget;
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
    public static void testSuccess() {
        System.out.println("Test success");
        MyAssert.assertTrue(true);
    }

    @MyTest
    public static void testError() {
        System.out.println("Test error");
        MyAssert.assertFalse(true);
    }


}
