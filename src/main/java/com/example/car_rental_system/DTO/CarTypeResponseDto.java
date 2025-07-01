package com.example.car_rental_system.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CarTypeResponseDto {
    private String typeName;
    private String description;
    private List<String> carsPlateNumbers;
}
