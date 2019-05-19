package com.dimipet.excelmerger.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AppProperties {
    // static variable single_instance of type Singleton 

    private static Properties prop = null;

    private AppProperties() {
    }

    public static Properties getInstance() {
        
        if (prop == null) {
            prop = new Properties();

            try (InputStream input = new FileInputStream("application.properties")) {

                prop.load(input);

            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }

        return prop;
    }
}
