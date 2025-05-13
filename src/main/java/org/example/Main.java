package org.example;

import org.example.config.DBConnection;
import org.example.config.PropertiesLoader;
import org.example.repository.*;
import org.example.service.*;

import java.util.Properties;

public class Main {

    public static void main(String[] args) {
        PropertiesLoader loader = new PropertiesLoader(new Properties());
        DBConnection connection = new DBConnection(loader);
        TransactionManager transactionManager = new TransactionManager(connection);

        DatabaseMaintenanceDAOIMPL dbMai = new DatabaseMaintenanceDAOIMPL(connection);
        dbMai.clearDatabase();

        DistributionService distributionService = new DistributionServiceIMPL(
                new DBGeneratorIMPL(new DataReaderIMPL(loader)),
                new CourseDAOIMPL(transactionManager),
                new GroupDAOIMPL(transactionManager),
                new StudentCourseDAOIMPL(transactionManager),
                new StudentDAOIMPL(transactionManager),
                new StudentGroupDistributionIMPL(),
                new StudentCourseDistributionIMPL());
        distributionService.distributeAll();
    }
}
//docker-compose up -d
//docker exec -it postgres_student_distribution psql -U myuser -d mydatabase