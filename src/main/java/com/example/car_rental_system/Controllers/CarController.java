package com.example.car_rental_system.Controllers;

import com.example.car_rental_system.Models.Car;
import com.example.car_rental_system.Models.CarStatus;
import com.example.car_rental_system.Services.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cars")
public class CarController {
    @Autowired
    private CarService carService;

    @PostMapping
    public Car createCar(@RequestBody Car car) {
        return carService.createCar(car);
    }

    @GetMapping
    public List<Car> getAllCars() {
        return carService.getAllCars();
    }

    @GetMapping("/{plateNumber}")
    public Car getCar(@PathVariable String plateNumber) {
        return carService.getCarByPlateNumber(plateNumber);
    }

    @PutMapping("/{plateNumber}")
    public Car updateCar(@PathVariable String plateNumber, @RequestBody Car car) {
        return carService.updateCar(plateNumber, car);
    }

    @DeleteMapping("/{plateNumber}")
    public void deleteCar(@PathVariable String plateNumber) {
        carService.deleteCar(plateNumber);
    }
    @GetMapping("/search/brand")
    public List<Car> searchByBrand(@RequestParam String brand) {
        return carService.searchByBrand(brand);
    }

    @GetMapping("/search/model")
    public List<Car> searchByModel(@RequestParam String model) {
        return carService.searchByModel(model);
    }

    @GetMapping("/search/status")
    public List<Car> searchByStatus(@RequestParam CarStatus status) {
        return carService.searchByStatus(status);
    }

}
