package com.example.car_rental_system.Controllers;
import com.example.car_rental_system.DTO.CarTypeRequsetDto;
import com.example.car_rental_system.DTO.CarTypeResponseDto;
import com.example.car_rental_system.Models.CarType;
import com.example.car_rental_system.Services.CarTypeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/car-types")
public class CarTypeController {
    private final CarTypeService carTypeService;

    public CarTypeController(CarTypeService carTypeService) {
        this.carTypeService = carTypeService;
    }

    @PostMapping
    public ResponseEntity<CarTypeResponseDto> createCarType(
           @RequestBody CarTypeRequsetDto carTypeRequestDto) {
        CarTypeResponseDto response = carTypeService.createCarType(carTypeRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<CarTypeResponseDto>> getAllCarTypes() {
        List<CarTypeResponseDto> carTypes = carTypeService.getAllCarTypes();
        return ResponseEntity.ok(carTypes);
    }

    @GetMapping("/{typeName}")
    public ResponseEntity<CarTypeResponseDto> getCarTypeById(
            @PathVariable String typeName) {
        CarTypeResponseDto carType = carTypeService.getCarTypeById(typeName);
        return ResponseEntity.ok(carType);
    }

    @PutMapping("/{typeName}")
    public ResponseEntity<CarTypeResponseDto> updateCarType(
            @PathVariable String typeName,
            @RequestBody CarTypeRequsetDto carTypeRequestDto) {
        CarTypeResponseDto updatedCarType = carTypeService.updateCarType(typeName, carTypeRequestDto);
        return ResponseEntity.ok(updatedCarType);
    }

    @DeleteMapping("/{typeName}")
    public ResponseEntity<Void> deleteCarType(
            @PathVariable String typeName) {
        carTypeService.deleteCarType(typeName);
        return ResponseEntity.noContent().build();
    }
}
