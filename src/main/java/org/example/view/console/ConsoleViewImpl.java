package org.example.view.console;

import java.util.Scanner;

public class ConsoleViewImpl implements ConsoleView {
    private final Scanner scanner;

    public ConsoleViewImpl(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public void showMessage(String message) {
        System.out.println(message);
    }

    @Override
    public String getUserInput(String prompt) {
        System.out.println(prompt);
        return scanner.nextLine();
    }
}
