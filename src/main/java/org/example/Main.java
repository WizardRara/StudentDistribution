package org.example;

import org.example.config.DBConnection;
import org.example.config.PropertiesLoader;
import org.example.repository.*;
import org.example.service.DBGeneratorIMPL;
import org.example.service.DataReaderIMPL;
import org.example.service.DistributionServiceIMPL;

import java.util.Properties;

public class Main {

    public static void main(String[] args) {
        PropertiesLoader loader = new PropertiesLoader(new Properties());
        DBConnection connection = new DBConnection(loader);

        DatabaseMaintenanceDAOIMPL dbMai = new DatabaseMaintenanceDAOIMPL(connection);
        dbMai.clearDatabase();

        DistributionServiceIMPL distributionServiceIMPL = new DistributionServiceIMPL(
                new DBGeneratorIMPL(new DataReaderIMPL(loader)),
                new CourseDAOIMPL(connection),
                new GroupDAOIMPL(connection),
                new StudentCourseDAOIMPL(connection),
                new StudentDAOIMPL(connection));
        distributionServiceIMPL.distributeAll();
    }
}
//docker-compose up -d
//docker exec -it postgres_student_distribution psql -U myuser -d mydatabase