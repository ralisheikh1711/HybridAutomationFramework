package com.automation.framework.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class ConfigReader {

    private static final Properties properties = new Properties();

    static {

        try (InputStream inputStream =
                     ConfigReader.class
                             .getClassLoader()
                             .getResourceAsStream("config/config.properties")) {

            if (inputStream == null) {
                throw new RuntimeException(
                        "config.properties not found"
                );
            }

            properties.load(inputStream);

        } catch (IOException e) {

            throw new RuntimeException(
                    "Unable to load config.properties",
                    e
            );
        }
    }

    private ConfigReader() {
    }

    public static String get(String key) {

        String value = System.getProperty(key);

        if (value == null || value.isBlank()) {
            value = properties.getProperty(key);
        }

        if (value == null) {
            throw new RuntimeException(
                    "Property not found: " + key
            );
        }

        return value;
    }

    public static String getBrowser() {
        return get("browser");
    }

    public static String getBaseUrl() {
        return get("baseUrl");
    }

    public static boolean isHeadless() {
        return Boolean.parseBoolean(get("headless"));
    }

    public static int getExplicitWait() {
        return Integer.parseInt(get("explicitWait"));
    }
}
