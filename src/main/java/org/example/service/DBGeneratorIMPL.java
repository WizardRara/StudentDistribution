package org.example.service;

import org.example.entity.Course;
import org.example.entity.Group;
import org.example.entity.Student;

import java.util.List;
import java.util.Random;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DBGeneratorIMPL implements DBGenerator {
    private final DataReaderIMPL dataReaderIMPL;
    private static final String FIRST_NAMES = "first_names";
    private static final String LAST_NAMES = "last_names";
    private static final String COURSES = "courses";
    private static final String DASH = "-";
    private static final String UNDERSCORE = "_";
    private static final String SPACE = " ";
    private static final Random random = new Random();

    public DBGeneratorIMPL(DataReaderIMPL dataReaderIMPL) {
        this.dataReaderIMPL = dataReaderIMPL;
    }

    @Override
    public List<Student> getStudentsList() {
        List<String> firstNames = dataReaderIMPL.readDataFromFile(FIRST_NAMES);
        List<String> lastNames = dataReaderIMPL.readDataFromFile(LAST_NAMES);
        return Stream.generate(() -> getRandomName(firstNames) +
                SPACE +
                getRandomName(lastNames))
                .distinct()
                .limit(200)
                .map(line -> createEntity(line, SPACE, Student::new))
                .collect(Collectors.toList());
    }

    @Override
    public List<Group> getGroupsList() {
        return Stream.generate(() -> String.valueOf(getRandomChar()) +
                        getRandomChar() +
                        DASH +
                        random.nextInt(10, 100))
                .distinct()
                .limit(10)
                .map(Group::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<Course> getCoursesList() {
        List<String> coursesAndDescriptions = dataReaderIMPL.readDataFromFile(COURSES);
        return coursesAndDescriptions.stream()
                .map(line -> createEntity(line, UNDERSCORE, Course::new))
                .collect(Collectors.toList());
    }

    private String getRandomName(List<String> names) {
        return names.get(random.nextInt(names.size()));
    }

    private <T> T createEntity (String line, String separator, BiFunction<String, String, T> constructor) {
        String [] lineSplit = line.split(separator);
        return constructor.apply(lineSplit[0], lineSplit[1]);
    }

    private char getRandomChar() {
        return (char) ('A' + random.nextInt(26));
    }
}
