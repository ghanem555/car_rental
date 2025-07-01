package com.example.car_rental_system.Services;

import com.example.car_rental_system.DTO.CarRequestDto;
import com.example.car_rental_system.DTO.CarResponseDto;
import com.example.car_rental_system.Mapper.CarMapper;
import com.example.car_rental_system.Models.Car;
import com.example.car_rental_system.Models.CarStatus;
import com.example.car_rental_system.Models.CarType;
import com.example.car_rental_system.Repository.CarRepository;
import com.example.car_rental_system.Repository.CarTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CarService {
    private final CarRepository carRepository;
    private final CarTypeRepository carTypeRepository;
    private final CarMapper carMapper;

    public CarResponseDto createCar(CarRequestDto carRequestDTO) {
        CarType carType = carTypeRepository.findById(carRequestDTO.getTypeName())
                .orElseThrow(() -> new RuntimeException("CarType not found"));

        if (carRepository.existsById(carRequestDTO.getPlateNumber())) {
            throw new RuntimeException("Car with plate number " + carRequestDTO.getPlateNumber() + " already exists");
        }
        Car car = carMapper.toEntity(carRequestDTO, carType);
        Car savedCar = carRepository.save(car);
        return carMapper.toDto(savedCar);
    }

    public List<CarResponseDto> getAllCars() {
        return carRepository.findAll().stream()
                .map(carMapper::toDto)
                .collect(Collectors.toList());
    }

    public CarResponseDto getCarByPlateNumber(String plateNumber) {
        Car car = carRepository.findById(plateNumber)
                .orElseThrow(() -> new RuntimeException("Car not found with plateNumber: " + plateNumber));
        return carMapper.toDto(car);
    }

    public CarResponseDto updateCar(String plateNumber, CarRequestDto carRequestDTO) {
        Car existingCar = carRepository.findById(plateNumber)
                .orElseThrow(() -> new RuntimeException("Car not found with plateNumber: " + plateNumber));

        CarType carType = carTypeRepository.findById(carRequestDTO.getTypeName())
                .orElseThrow(() -> new RuntimeException("CarType not found"));

        // Update only allowed fields (excluding plateNumber)
        existingCar.setBrand(carRequestDTO.getBrand());
        existingCar.setModel(carRequestDTO.getModel());
        existingCar.setDailyRate(carRequestDTO.getDailyRate());
        existingCar.setCarType(carType);

        Car updatedCar = carRepository.save(existingCar);
        return carMapper.toDto(updatedCar);
    }

    public void deleteCar(String plateNumber) {
        if (!carRepository.existsById(plateNumber)) {
            throw new RuntimeException("Car not found with plateNumber: " + plateNumber);
        }
        carRepository.deleteById(plateNumber);
    }


}
