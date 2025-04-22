package org.example.service;

import java.util.List;

public interface DataReader {
    List<String> readDataFromFile(String fileName);
}
