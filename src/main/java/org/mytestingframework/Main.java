package org.mytestingframework;

import org.mytestingframework.MyTestingFramework;
import org.mytestingframework.annotations.MyTest;
import org.mytestingframework.annotations.MyTesterTarget;
import org.mytestingframework.constant.About;

import static org.mytestingframework.constant.Color.*;

@MyTesterTarget
public class Main {
    public static void main(String[] args) {
        try {
            MyTestingFramework.run("");
            System.out.println("test");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @MyTest
    public static boolean testSuccess() {
        System.out.println("Test success");
        return true;
    }

    @MyTest
    public static Boolean testError() {
        System.out.println("Test error");
        return false;
    }


}
