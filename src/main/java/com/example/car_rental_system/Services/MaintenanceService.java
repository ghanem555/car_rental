package com.example.car_rental_system.Services;
import com.example.car_rental_system.Models.Car;
import com.example.car_rental_system.Models.CarStatus;
import com.example.car_rental_system.Models.Maintenance;
import com.example.car_rental_system.Repository.CarRepository;
import com.example.car_rental_system.Repository.MaintenanceRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MaintenanceService {
    private final MaintenanceRepository maintenanceRepository;
    private final CarRepository carRepository;

    public MaintenanceService(MaintenanceRepository maintenanceRepository,
                              CarRepository carRepository) {
        this.maintenanceRepository = maintenanceRepository;
        this.carRepository = carRepository;
    }

    public Maintenance createMaintenance(Maintenance maintenance) {
        Car car = carRepository.findById(maintenance.getCar().getPlateNumber())
                .orElseThrow(() -> new RuntimeException("Car not found"));

        car.setStatus(CarStatus.MAINTENANCE);
        carRepository.save(car);

        return maintenanceRepository.save(maintenance);
    }

    public List<Maintenance> getAllMaintenances() {
        return maintenanceRepository.findAll();
    }

    public Maintenance getMaintenanceById(long id) {
        return maintenanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Maintenance not found with id: " + id));
    }

    public Maintenance updateMaintenance(long id, Maintenance maintenanceDetails) {
        Maintenance maintenance = getMaintenanceById(id);
        maintenance.setStartDate(maintenanceDetails.getStartDate());
        maintenance.setEndDate(maintenanceDetails.getEndDate());
        maintenance.setIssueType(maintenanceDetails.getIssueType());
        maintenance.setDescription(maintenanceDetails.getDescription());
        maintenance.setCost(maintenanceDetails.getCost());
        maintenance.setStatus(maintenanceDetails.getStatus());
        return maintenanceRepository.save(maintenance);
    }

    public void deleteMaintenance(long id) {
        maintenanceRepository.deleteById(id);
    }

}
