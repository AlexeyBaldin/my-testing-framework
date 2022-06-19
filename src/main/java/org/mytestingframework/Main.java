package org.mytestingframework;

import org.mytestingframework.MyTestingFramework;

public class Main {
    public static void main(String[] args) {
        try {
            MyTestingFramework.run("");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
