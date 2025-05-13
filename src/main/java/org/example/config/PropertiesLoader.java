package org.example.config;

import org.example.Exeption.FileReadException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class PropertiesLoader {
    private final Properties properties;

    public PropertiesLoader(Properties properties) {
        this.properties = properties;
        try {
            properties.load(Files.newBufferedReader(Path.of("src/main/resources/application.properties")));
        } catch (IOException e) {
            throw new FileReadException("File read error: " , e);
        }
    }

    public String getProperty(String string) {
        return properties.getProperty(string);
    }
}
