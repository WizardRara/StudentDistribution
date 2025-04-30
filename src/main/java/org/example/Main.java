package org.example;

import org.example.config.DBConnection;
import org.example.config.PropertiesLoader;
import org.example.repository.*;
import org.example.service.DBGeneratorIMPL;
import org.example.service.DataReaderIMPL;
import org.example.service.DistributionService;
import org.example.service.DistributionServiceIMPL;

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
                new CourseWriteDAOIMPL(transactionManager),
                new GroupWriteDAOIMPL(transactionManager),
                new StudentCourseWriteDAOIMPL(transactionManager),
                new StudentWriteDAOIMPL(transactionManager));
        distributionService.distributeAll();
    }
}
//docker-compose up -d
//docker exec -it postgres_student_distribution psql -U myuser -d mydatabase