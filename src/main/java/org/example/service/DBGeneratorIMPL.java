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
    private final DataReader dataReader;
    private static final Random random = new Random();

    public DBGeneratorIMPL(DataReader dataReader) {
        this.dataReader = dataReader;
    }

    @Override
    public List<Student> getStudentsList() {
        List<String> firstNames = dataReader.readStudentsFirstNames();
        List<String> lastNames = dataReader.readStudentsLastNames();
        return Stream.generate(() ->"%s_%s".formatted(getRandomName(firstNames), getRandomName(lastNames)))
                .distinct()
                .limit(200)
                .map(line -> createEntity(line, Student::new))
                .collect(Collectors.toList());
    }

    @Override
    public List<Group> getGroupsList() {
        return Stream.generate(() -> "%c%c-%d".formatted(getRandomChar(), getRandomChar(),
                        random.nextInt(90) + 10))
                .distinct()
                .limit(10)
                .map(Group::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<Course> getCoursesList() {
        List<String> coursesAndDescriptions = dataReader.readCourses();
        return coursesAndDescriptions.stream()
                .map(line -> createEntity(line, Course::new))
                .collect(Collectors.toList());
    }

    private String getRandomName(List<String> names) {
        return names.get(random.nextInt(names.size()));
    }

    private <T> T createEntity (String line, BiFunction<String, String, T> constructor) {
        String [] lineSplit = line.split("_");
        return constructor.apply(lineSplit[0], lineSplit[1]);
    }

    private char getRandomChar() {
        return (char) ('A' + random.nextInt(26));
    }
}
