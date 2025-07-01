package com.example.car_rental_system.Mapper;

import com.example.car_rental_system.DTO.CarRequestDto;
import com.example.car_rental_system.DTO.CarResponseDto;
import com.example.car_rental_system.Models.Car;
import com.example.car_rental_system.Models.CarStatus;
import com.example.car_rental_system.Models.CarType;
import org.springframework.stereotype.Component;

@Component
public class CarMapper {
    public Car toEntity(CarRequestDto dto, CarType carType) {
        Car car = new Car();
        car.setPlateNumber(dto.getPlateNumber());
        car.setBrand(dto.getBrand());
        car.setModel(dto.getModel());
        car.setDailyRate(dto.getDailyRate());
        car.setCarType(carType);
        car.setStatus(CarStatus.AVAILABLE);
        return car;
    }

    public CarResponseDto toDto(Car car) {
        CarResponseDto dto = new CarResponseDto();
        dto.setPlateNumber(car.getPlateNumber());
        dto.setBrand(car.getBrand());
        dto.setModel(car.getModel());
        dto.setDailyRate(car.getDailyRate());
        dto.setStatus(car.getStatus());
        dto.setLastMaintenance(car.getLastMaintenance());
        dto.setTypeName(car.getCarType().getTypeName());
        return dto;
    }
}
