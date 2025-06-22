package Services;

import Models.Car;
import Repository.CarRepository;
import Repository.CarTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarService {
    private final CarRepository carRepository;
    private final CarTypeRepository carTypeRepository;

    public Car createCar(Car car) {
        if (!carTypeRepository.existsById(car.getCarType().getTypeName())) {
            throw new RuntimeException("Invalid car type");
        }
        return carRepository.save(car);
    }

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    public Car getCarByPlateNumber(String plateNumber) {
        return carRepository.findById(plateNumber)
                .orElseThrow(() -> new RuntimeException("Car not found"));
    }

    public Car updateCar(String plateNumber, Car carDetails) {
        Car car = getCarByPlateNumber(plateNumber);
        car.setBrand(carDetails.getBrand());
        car.setModel(carDetails.getModel());
        car.setDailyRate(carDetails.getDailyRate());
        car.setCarType(carDetails.getCarType());
        car.setLastMaintenance(carDetails.getLastMaintenance());
        car.setStatus(carDetails.getStatus());
        car.setPlateNumber(carDetails.getPlateNumber());
        return carRepository.save(car);
    }

    public void deleteCar(String plateNumber) {
        carRepository.deleteById(plateNumber);
    }
}
