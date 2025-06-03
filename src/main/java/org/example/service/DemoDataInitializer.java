package org.example.service;

import org.example.repository.DatabaseMaintenanceDAO;

public class DemoDataInitializer {
    private final DatabaseMaintenanceDAO dbMai;
    private final DistributionService distributionService;

    public DemoDataInitializer(DatabaseMaintenanceDAO dbMai, DistributionService distributionService) {
        this.dbMai = dbMai;
        this.distributionService = distributionService;
    }

    public void resetAndFillDemoData() {
        dbMai.clearDatabase();
        distributionService.distributeAll();
    }
}
