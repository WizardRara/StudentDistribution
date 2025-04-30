package org.example.service;

import java.util.List;

public interface DataReader {
    List<String> readStudentsFirstNames();
    List<String> readStudentsLastNames();
    List<String> readCourses();
}
