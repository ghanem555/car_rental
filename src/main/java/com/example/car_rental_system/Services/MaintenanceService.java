package com.example.car_rental_system.Services;
import com.example.car_rental_system.DTO.MaintenanceRequsetDto;
import com.example.car_rental_system.DTO.MaintenanceResponseDto;
import com.example.car_rental_system.Mapper.MaintenanceMapper;
import com.example.car_rental_system.Models.Car;
import com.example.car_rental_system.Models.CarStatus;
import com.example.car_rental_system.Models.Maintenance;
import com.example.car_rental_system.Models.MaintenanceStatus;
import com.example.car_rental_system.Repository.CarRepository;
import com.example.car_rental_system.Repository.MaintenanceRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MaintenanceService {
    private final MaintenanceRepository maintenanceRepository;
    private final CarRepository carRepository;
    private final MaintenanceMapper maintenanceMapper;

    public MaintenanceService(MaintenanceRepository maintenanceRepository,
                              CarRepository carRepository, MaintenanceMapper maintenanceMapper) {
        this.maintenanceRepository = maintenanceRepository;
        this.carRepository = carRepository;
        this.maintenanceMapper = maintenanceMapper;
    }

    public MaintenanceResponseDto createMaintenance(MaintenanceRequsetDto maintenanceRequestDto) {
        Car car = carRepository.findById(maintenanceRequestDto.getPlateNumber())
                .orElseThrow(() -> new RuntimeException("Car not found with plate: " + maintenanceRequestDto.getPlateNumber()));

        Maintenance maintenance = maintenanceMapper.toEntity(maintenanceRequestDto, car);
        Maintenance savedMaintenance = maintenanceRepository.save(maintenance);

        // Update car status
        car.setStatus(CarStatus.MAINTENANCE);
        carRepository.save(car);

        return maintenanceMapper.toDto(savedMaintenance);
    }


    public List<MaintenanceResponseDto> getAllMaintenances() {
        return maintenanceRepository.findAll().stream()
                .map(maintenanceMapper::toDto)
                .collect(Collectors.toList());
    }

    public MaintenanceResponseDto getMaintenanceById(Long id) {
        Maintenance maintenance = maintenanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Maintenance not found with ID: " + id));
        return maintenanceMapper.toDto(maintenance);
    }

    public MaintenanceResponseDto updateMaintenance(Long id, MaintenanceRequsetDto maintenanceRequestDto) {
        Maintenance existingMaintenance = maintenanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Maintenance not found with ID: " + id));

        Car car = carRepository.findById(maintenanceRequestDto.getPlateNumber())
                .orElseThrow(() -> new RuntimeException("Car not found with plate: " + maintenanceRequestDto.getPlateNumber()));

        // Update maintenance details
        existingMaintenance.setCar(car);
        existingMaintenance.setStartDate(maintenanceRequestDto.getStartDate());
        existingMaintenance.setEndDate(maintenanceRequestDto.getEndDate());
        existingMaintenance.setIssueType(maintenanceRequestDto.getIssueType());
        existingMaintenance.setDescription(maintenanceRequestDto.getDescription());
        existingMaintenance.setCost(maintenanceRequestDto.getCost());

        Maintenance updatedMaintenance = maintenanceRepository.save(existingMaintenance);
        return maintenanceMapper.toDto(updatedMaintenance);
    }


    public void deleteMaintenance(Long id) {
        Maintenance maintenance = maintenanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Maintenance not found with ID: " + id));

        // Return car to available status if this was the only maintenance
        Car car = maintenance.getCar();
        if (maintenanceRepository.countByCarAndStatusNot(car, MaintenanceStatus.COMPLETED) <= 1) {
            car.setStatus(CarStatus.AVAILABLE);
            carRepository.save(car);
        }

        maintenanceRepository.deleteById(id);
    }

    public List<MaintenanceResponseDto> getMaintenancesByCar(String plateNumber) {
        Car car = carRepository.findById(plateNumber)
                .orElseThrow(() -> new RuntimeException("Car not found with plate: " + plateNumber));

        return maintenanceRepository.findByCar(car).stream()
                .map(maintenanceMapper::toDto)
                .collect(Collectors.toList());
    }
}
