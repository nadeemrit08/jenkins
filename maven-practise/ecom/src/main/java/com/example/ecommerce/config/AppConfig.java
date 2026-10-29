package com.example.ecommerce.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

public final class AppConfig {
    private static final String CONFIG_FILE = "db.properties";

    private final String dbUrl;
    private final String dbUser;
    private final String dbPassword;

    private AppConfig(String dbUrl, String dbUser, String dbPassword) {
        this.dbUrl = dbUrl;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
    }

    public static AppConfig load() {
        Properties properties = new Properties();
        try (InputStream inputStream = AppConfig.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (inputStream != null) {
                properties.load(inputStream);
            }
        } catch (IOException e) {
            throw new IllegalStateException("Unable to read " + CONFIG_FILE, e);
        }

        String url = firstNonBlank(System.getenv("DB_URL"), properties.getProperty("db.url"));
        String user = firstNonBlank(System.getenv("DB_USER"), properties.getProperty("db.user"));
        String password = firstNonBlank(System.getenv("DB_PASSWORD"), properties.getProperty("db.password"));

        return new AppConfig(
                require(url, missingConfigMessage("db.url", "DB_URL")),
                require(user, missingConfigMessage("db.user", "DB_USER")),
                Objects.requireNonNullElse(password, "")
        );
    }

    public String dbUrl() {
        return dbUrl;
    }

    public String dbUser() {
        return dbUser;
    }

    public String dbPassword() {
        return dbPassword;
    }

    private static String firstNonBlank(String first, String second) {
        if (first != null && !first.isBlank()) {
            return first;
        }
        return second != null && !second.isBlank() ? second : null;
    }

    private static String require(String value, String message) {
        if (value == null || value.isBlank()) {
            throw new IllegalStateException(message);
        }
        return value;
    }

    private static String missingConfigMessage(String propertyName, String environmentVariable) {
        return "Missing database config: " + propertyName
                + ". Create src/main/resources/db.properties from db.properties.example "
                + "or set the " + environmentVariable + " environment variable.";
    }
}
