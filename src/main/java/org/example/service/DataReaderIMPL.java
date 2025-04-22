package org.example.service;

import org.example.config.PropertiesLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class DataReaderIMPL implements DataReader {
    private final PropertiesLoader propertiesLoader;

    public DataReaderIMPL(PropertiesLoader propertiesLoader) {
        this.propertiesLoader = propertiesLoader;
    }

    @Override
    public List<String> readDataFromFile(String fileName) {
        try (var line = Files.lines(Path.of(propertiesLoader.getProperty(fileName)))) {
            return line.map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при чтении файла " + e.getMessage(), e);
        }
    }
}

