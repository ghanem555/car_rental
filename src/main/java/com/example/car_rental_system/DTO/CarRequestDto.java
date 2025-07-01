package com.example.car_rental_system.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CarRequestDto {
    private String plateNumber;
    private String brand;
    private String model;
    private BigDecimal dailyRate;
    private String typeName;
}
