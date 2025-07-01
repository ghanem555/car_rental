package com.example.car_rental_system.Services;
import com.example.car_rental_system.DTO.CarTypeRequsetDto;
import com.example.car_rental_system.DTO.CarTypeResponseDto;
import com.example.car_rental_system.Mapper.CarTypeMapper;
import com.example.car_rental_system.Models.CarType;
import com.example.car_rental_system.Repository.CarTypeRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarTypeService {
    private final CarTypeRepository carTypeRepository;
    private  final CarTypeMapper carTypeMapper;

    public CarTypeService(CarTypeRepository carTypeRepository, CarTypeMapper carTypeMapper) {
        this.carTypeRepository = carTypeRepository;
        this.carTypeMapper = carTypeMapper;
    }

    public CarTypeResponseDto createCarType(CarTypeRequsetDto carTypeRequestDto) {
        // Check if car type already exists
        if (carTypeRepository.existsById(carTypeRequestDto.getTypeName())) {
            throw new RuntimeException("CarType with name '" + carTypeRequestDto.getTypeName() + "' already exists");
        }

        CarType carType = carTypeMapper.toEntity(carTypeRequestDto);
        CarType savedCarType = carTypeRepository.save(carType);
        return carTypeMapper.toDto(savedCarType);
    }

    public List<CarTypeResponseDto> getAllCarTypes() {
        return carTypeRepository.findAll().stream()
                .map(carTypeMapper::toDto)
                .collect(Collectors.toList());
    }

    public CarTypeResponseDto getCarTypeById(String typeName) {
        CarType carType = carTypeRepository.findById(typeName)
                .orElseThrow(() -> new RuntimeException("CarType not found with name: " + typeName));
        return carTypeMapper.toDto(carType);
    }

    public CarTypeResponseDto updateCarType(String typeName, CarTypeRequsetDto carTypeRequestDto) {
        CarType carType = carTypeRepository.findById(typeName)
                .orElseThrow(() -> new RuntimeException("CarType not found with name: " + typeName));

        carType.setDescription(carTypeRequestDto.getDescription());

        CarType updatedCarType = carTypeRepository.save(carType);
        return carTypeMapper.toDto(updatedCarType);
    }

    public void deleteCarType(String typeName) {
        if (!carTypeRepository.existsById(typeName)) {
            throw new RuntimeException("CarType not found with name: " + typeName);
        }
        carTypeRepository.deleteById(typeName);
    }
}
