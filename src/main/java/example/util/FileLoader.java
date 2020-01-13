package main.java.example.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class FileLoader {
    public static Map<String, String> loadProperties(String filepath) throws IOException {
        Properties properties = new Properties();

        try (InputStream in = new FileInputStream(filepath)) {
            Map<String, String> propMap = new HashMap<>();

            properties.load(in);
            properties.stringPropertyNames()
                    .forEach(name -> propMap.put(name, properties.getProperty(name)));

            return propMap;
        }
    }
}
