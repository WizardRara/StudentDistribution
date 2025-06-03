package org.example;

import org.example.config.AppContainer;
import org.example.service.*;

import org.example.view.menu.UserInterface;

public class Main {
    public static void main(String[] args) {
        AppContainer appContainer = new AppContainer();
        DemoDataInitializer demoDataInitializer = appContainer.getDemoDataInitializer();
        demoDataInitializer.resetAndFillDemoData();

        UserInterface userInterface = appContainer.getUserInterface();
        userInterface.displayUserFunctions();
    }
}
//docker-compose up -d
//docker exec -it postgres_student_distribution psql -U myuser -d mydatabase