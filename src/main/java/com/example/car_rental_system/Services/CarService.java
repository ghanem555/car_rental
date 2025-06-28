package com.example.car_rental_system.Services;

import com.example.car_rental_system.Models.Car;
import com.example.car_rental_system.Models.CarStatus;
import com.example.car_rental_system.Repository.CarRepository;
import com.example.car_rental_system.Repository.CarTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarService {
    private final CarRepository carRepository;
    private final CarTypeRepository carTypeRepository;
    public Car createCar(Car car) {
        // Verify car type exists
        carTypeRepository.findById(car.getCarType().getTypeName())
                .orElseThrow(() -> new RuntimeException("CarType not found"));
        return carRepository.save(car);
    }

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    public Car getCarByPlateNumber(String plateNumber) {
        return carRepository.findById(plateNumber)
                .orElseThrow(() -> new RuntimeException("Car not found with plateNumber: " + plateNumber));
    }

    public Car updateCar(String plateNumber, Car carDetails) {
        Car car = getCarByPlateNumber(plateNumber);
        car.setBrand(carDetails.getBrand());
        car.setModel(carDetails.getModel());
        car.setDailyRate(carDetails.getDailyRate());
        car.setStatus(carDetails.getStatus());
        car.setLastMaintenance(carDetails.getLastMaintenance());
        car.setCarType(carDetails.getCarType());
        return carRepository.save(car);
    }

    public void deleteCar(String plateNumber) {
        carRepository.deleteById(plateNumber);
    }

    public List<Car> searchByBrand(String brand) {
        return carRepository.findAll().stream()
                .filter(car -> car.getBrand().equalsIgnoreCase(brand))
                .toList();
    }

    public List<Car> searchByModel(String model) {
        return carRepository.findAll().stream()
                .filter(car -> car.getModel().equalsIgnoreCase(model))
                .toList();
    }

    public List<Car> searchByStatus(CarStatus status) {
        return carRepository.findAll().stream()
                .filter(car -> car.getStatus() == status)
                .toList();
    }
}
