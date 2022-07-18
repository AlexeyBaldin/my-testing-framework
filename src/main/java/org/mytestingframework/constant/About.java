package org.mytestingframework.constant;

import java.io.IOException;
import java.util.Properties;

/**
 * Class with constants with information about project.
 */
public final class About {
    private About() {}

    private static final Properties properties = new Properties();
    static {
        try {
            properties.load(About.class.getClassLoader().getResourceAsStream("application.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static final String AUTHOR_NAME = "Alexey";
    public static final String AUTHOR_SNAME = "Baldin";
    public static final String VERSION = properties.getProperty("version");

}
