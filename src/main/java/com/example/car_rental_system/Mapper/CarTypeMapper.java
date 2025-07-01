package com.example.car_rental_system.Mapper;

import com.example.car_rental_system.DTO.CarTypeRequsetDto;
import com.example.car_rental_system.DTO.CarTypeResponseDto;
import com.example.car_rental_system.Models.Car;
import com.example.car_rental_system.Models.CarType;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CarTypeMapper {

    public CarType toEntity(CarTypeRequsetDto dto) {
        CarType carType = new CarType();
        carType.setTypeName(dto.getTypeName());
        carType.setDescription(dto.getDescription());
        return carType;
    }

    public CarTypeResponseDto toDto(CarType carType) {
        CarTypeResponseDto dto = new CarTypeResponseDto();
        dto.setTypeName(carType.getTypeName());
        dto.setDescription(carType.getDescription());

        if (carType.getCars() != null) {
            dto.setCarsPlateNumbers(carType.getCars().stream()
                    .map(Car::getPlateNumber)
                    .collect(Collectors.toList()));
        }

        return dto;
    }
}
