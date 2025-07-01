package com.example.car_rental_system.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RentalRequsetDto {
    private Long customerId;
    private String plateNumber;
    private LocalDate startDate;
    private LocalDate endDate;
}
