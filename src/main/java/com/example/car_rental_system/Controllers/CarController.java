package com.example.car_rental_system.Controllers;

import com.example.car_rental_system.DTO.CarRequestDto;
import com.example.car_rental_system.DTO.CarResponseDto;
import com.example.car_rental_system.Models.Car;
import com.example.car_rental_system.Models.CarStatus;
import com.example.car_rental_system.Services.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/cars")
public class CarController {
    @Autowired
    private CarService carService;

    @PostMapping
    public ResponseEntity<CarResponseDto> createCar(@RequestBody CarRequestDto carRequestDTO) {
        CarResponseDto response = carService.createCar(carRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<CarResponseDto>> getAllCars() {
        List<CarResponseDto> cars = carService.getAllCars();
        return ResponseEntity.ok(cars);
    }

    @GetMapping("/{plateNumber}")
    public ResponseEntity<CarResponseDto> getCarByPlateNumber(@PathVariable String plateNumber) {
        CarResponseDto car = carService.getCarByPlateNumber(plateNumber);
        return ResponseEntity.ok(car);
    }


    @PutMapping("/{plateNumber}")
    public ResponseEntity<CarResponseDto> updateCar(
            @PathVariable String plateNumber,
             @RequestBody CarRequestDto carRequestDTO) {
        CarResponseDto updatedCar = carService.updateCar(plateNumber, carRequestDTO);
        return ResponseEntity.ok(updatedCar);
    }

    @DeleteMapping("/{plateNumber}")
    public ResponseEntity<Void> deleteCar(@PathVariable String plateNumber) {
        carService.deleteCar(plateNumber);
        return ResponseEntity.noContent().build();
    }


}
