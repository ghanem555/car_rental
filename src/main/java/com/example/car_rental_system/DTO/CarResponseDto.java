package com.example.car_rental_system.DTO;
import com.example.car_rental_system.Models.CarStatus;
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
public class CarResponseDto {
    private String plateNumber;
    private String brand;
    private String model;
    private BigDecimal dailyRate;
    private CarStatus status;
    private LocalDate lastMaintenance;
    private String typeName;
}
