package com.example.car_rental_system.Controllers;
import com.example.car_rental_system.DTO.MaintenanceRequsetDto;
import com.example.car_rental_system.DTO.MaintenanceResponseDto;
import com.example.car_rental_system.Models.Maintenance;
import com.example.car_rental_system.Services.MaintenanceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<MaintenanceResponseDto> createMaintenance(
             @RequestBody MaintenanceRequsetDto maintenanceRequestDto) {
        MaintenanceResponseDto response = maintenanceService.createMaintenance(maintenanceRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<MaintenanceResponseDto>> getAllMaintenances() {
        List<MaintenanceResponseDto> maintenances = maintenanceService.getAllMaintenances();
        return ResponseEntity.ok(maintenances);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MaintenanceResponseDto> getMaintenanceById(
            @PathVariable Long id) {
        MaintenanceResponseDto maintenance = maintenanceService.getMaintenanceById(id);
        return ResponseEntity.ok(maintenance);
    }
    @PutMapping("/{id}")
    public ResponseEntity<MaintenanceResponseDto> updateMaintenance(
            @PathVariable Long id,
             @RequestBody MaintenanceRequsetDto maintenanceRequestDto) {
        MaintenanceResponseDto updatedMaintenance = maintenanceService.updateMaintenance(id, maintenanceRequestDto);
        return ResponseEntity.ok(updatedMaintenance);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMaintenance(
            @PathVariable Long id) {
        maintenanceService.deleteMaintenance(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/car/{plateNumber}")
    public ResponseEntity<List<MaintenanceResponseDto>> getMaintenancesByCar(
            @PathVariable String plateNumber) {
        List<MaintenanceResponseDto> maintenances = maintenanceService.getMaintenancesByCar(plateNumber);
        return ResponseEntity.ok(maintenances);
    }

}
