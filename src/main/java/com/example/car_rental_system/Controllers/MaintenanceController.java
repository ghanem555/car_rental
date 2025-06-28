package com.example.car_rental_system.Controllers;
import com.example.car_rental_system.Models.Maintenance;
import com.example.car_rental_system.Services.MaintenanceService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/maintenances")
public class MaintenanceController {
    private final MaintenanceService maintenanceService;

    public MaintenanceController(MaintenanceService maintenanceService) {
        this.maintenanceService = maintenanceService;
    }

    @PostMapping
    public Maintenance createMaintenance(@RequestBody Maintenance maintenance) {
        return maintenanceService.createMaintenance(maintenance);
    }

    @GetMapping
    public List<Maintenance> getAllMaintenances() {
        return maintenanceService.getAllMaintenances();
    }

    @GetMapping("/{id}")
    public Maintenance getMaintenanceById(@PathVariable long id) {
        return maintenanceService.getMaintenanceById(id);
    }

    @PutMapping("/{id}")
    public Maintenance updateMaintenance(@PathVariable long id, @RequestBody Maintenance maintenance) {
        return maintenanceService.updateMaintenance(id, maintenance);
    }

    @DeleteMapping("/{id}")
    public void deleteMaintenance(@PathVariable long id) {
        maintenanceService.deleteMaintenance(id);
    }

}
