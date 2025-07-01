package com.example.car_rental_system.DTO;

import com.example.car_rental_system.Models.MaintenanceStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MaintenanceResponseDto {
    private Long maintenanceId;
    private String plateNumber;
    private String carInfo;
    private LocalDate startDate;
    private LocalDate endDate;
    private String issueType;
    private String description;
    private BigDecimal cost;
    private MaintenanceStatus status;
}
