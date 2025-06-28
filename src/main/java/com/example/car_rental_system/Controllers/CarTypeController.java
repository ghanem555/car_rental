package com.example.car_rental_system.Controllers;
import com.example.car_rental_system.Models.CarType;
import com.example.car_rental_system.Services.CarTypeService;
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
    public CarType createCarType(@RequestBody CarType carType) {
        return carTypeService.createCarType(carType);
    }

    @GetMapping
    public List<CarType> getAllCarTypes() {
        return carTypeService.getAllCarTypes();
    }

    @GetMapping("/{typeName}")
    public CarType getCarTypeById(@PathVariable String typeName) {
        return carTypeService.getCarTypeById(typeName);
    }

    @PutMapping("/{typeName}")
    public CarType updateCarType(@PathVariable String typeName, @RequestBody CarType carType) {
        return carTypeService.updateCarType(typeName, carType);
    }

    @DeleteMapping("/{typeName}")
    public void deleteCarType(@PathVariable String typeName) {
        carTypeService.deleteCarType(typeName);
    }
}
