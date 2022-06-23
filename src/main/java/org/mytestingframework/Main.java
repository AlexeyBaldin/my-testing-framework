package org.mytestingframework;

import org.mytestingframework.MyTestingFramework;
import org.mytestingframework.annotations.MyTest;
import org.mytestingframework.annotations.MyTesterTarget;
import static org.mytestingframework.constant.Color.*;

@MyTesterTarget
public class Main {
    public static void main(String[] args) {
        try {
            MyTestingFramework.run("");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @MyTest
    public static boolean testSuccess() {
        System.out.println(ANSI_BLUE + "Test success" + ANSI_RESET);
        return true;
    }
}
