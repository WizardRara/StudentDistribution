package org.example.service;

import org.example.config.PropertiesLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class DataReaderIMPL implements DataReader {
    private final PropertiesLoader propertiesLoader;
    private static final String FIRST_NAMES = "first_names";
    private static final String LAST_NAMES = "last_names";
    private static final String COURSES = "courses";

    public DataReaderIMPL(PropertiesLoader propertiesLoader) {
        this.propertiesLoader = propertiesLoader;
    }

    @Override
    public List<String> readStudentsFirstNames() {
        return readDataFromFile(FIRST_NAMES);
    }

    @Override
    public List<String> readStudentsLastNames() {
        return readDataFromFile(LAST_NAMES);
    }

    @Override
    public List<String> readCourses() {
        return readDataFromFile(COURSES);
    }

    private List<String> readDataFromFile(String fileName) {
        try (var line = Files.lines(Path.of(propertiesLoader.getProperty(fileName)))) {
            return line.map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при чтении файла " + e.getMessage(), e);
        }
    }
}

