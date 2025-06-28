package com.example.car_rental_system.Services;
import com.example.car_rental_system.Models.CarType;
import com.example.car_rental_system.Repository.CarTypeRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CarTypeService {
    private final CarTypeRepository carTypeRepository;

    public CarTypeService(CarTypeRepository carTypeRepository) {
        this.carTypeRepository = carTypeRepository;
    }

    public CarType createCarType(CarType carType) {
        return carTypeRepository.save(carType);
    }

    public List<CarType> getAllCarTypes() {
        return carTypeRepository.findAll();
    }

    public CarType getCarTypeById(String typeName) {
        return carTypeRepository.findById(typeName)
                .orElseThrow(() -> new RuntimeException("CarType not found with typeName: " + typeName));
    }

    public CarType updateCarType(String typeName, CarType carTypeDetails) {
        CarType carType = getCarTypeById(typeName);
        carType.setDescription(carTypeDetails.getDescription());
        return carTypeRepository.save(carType);
    }

    public void deleteCarType(String typeName) {
        carTypeRepository.deleteById(typeName);
    }
}