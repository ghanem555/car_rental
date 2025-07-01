package com.example.car_rental_system.Mapper;


import com.example.car_rental_system.DTO.MaintenanceRequsetDto;
import com.example.car_rental_system.DTO.MaintenanceResponseDto;
import com.example.car_rental_system.Models.Car;
import com.example.car_rental_system.Models.Maintenance;
import com.example.car_rental_system.Models.MaintenanceStatus;
import org.springframework.stereotype.Component;

@Component
public class MaintenanceMapper {

    public Maintenance toEntity(MaintenanceRequsetDto dto, Car car) {
        Maintenance maintenance = new Maintenance();
        maintenance.setCar(car);
        maintenance.setStartDate(dto.getStartDate());
        maintenance.setEndDate(dto.getEndDate());
        maintenance.setIssueType(dto.getIssueType());
        maintenance.setDescription(dto.getDescription());
        maintenance.setCost(dto.getCost());
        maintenance.setStatus(MaintenanceStatus.PENDING);
        return maintenance;
    }

    public MaintenanceResponseDto toDto(Maintenance maintenance) {
        MaintenanceResponseDto dto = new MaintenanceResponseDto();
        dto.setMaintenanceId(maintenance.getMaintenanceId());
        dto.setPlateNumber(maintenance.getCar().getPlateNumber());
        dto.setCarInfo(maintenance.getCar().getBrand() + " " + maintenance.getCar().getModel());
        dto.setStartDate(maintenance.getStartDate());
        dto.setEndDate(maintenance.getEndDate());
        dto.setIssueType(maintenance.getIssueType());
        dto.setDescription(maintenance.getDescription());
        dto.setCost(maintenance.getCost());
        dto.setStatus(maintenance.getStatus());
        return dto;
    }
}
