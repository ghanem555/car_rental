package com.example.car_rental_system.DTO;

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
public class MaintenanceRequsetDto {
    private String plateNumber;
    private LocalDate startDate;
    private LocalDate endDate;
    private String issueType;
    private String description;
    private BigDecimal cost;
}
