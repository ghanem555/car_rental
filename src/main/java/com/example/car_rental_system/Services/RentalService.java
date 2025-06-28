package com.example.car_rental_system.Services;

import com.example.car_rental_system.Models.Car;
import com.example.car_rental_system.Models.CarStatus;
import com.example.car_rental_system.Models.Rental;
import com.example.car_rental_system.Models.RentalStatus;
import com.example.car_rental_system.Repository.CarRepository;
import com.example.car_rental_system.Repository.CustomerRepository;
import com.example.car_rental_system.Repository.RentalRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class RentalService {
    private final RentalRepository rentalRepository;
    private final CustomerRepository customerRepository;
    private final CarRepository carRepository;

    public RentalService(RentalRepository rentalRepository,
                         CustomerRepository customerRepository,
                         CarRepository carRepository) {
        this.rentalRepository = rentalRepository;
        this.customerRepository = customerRepository;
        this.carRepository = carRepository;
    }

    public Rental createRental(Rental rental) {
        // Validate customer exists
        customerRepository.findById(rental.getCustomer().getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found with ID: " + rental.getCustomer().getCustomerId()));

        // Validate car exists
        Car car = carRepository.findById(rental.getCar().getPlateNumber())
                .orElseThrow(() -> new RuntimeException("Car not found with plate: " + rental.getCar().getPlateNumber()));

        // Validate rental dates
        validateRentalDates(rental);

        // Check car availability
        validateCarAvailability(car, rental.getStartDate(), rental.getEndDate());

        // Calculate price and update car status
        long days = rental.getEndDate().toEpochDay() - rental.getStartDate().toEpochDay();
        rental.setTotalPrice(car.getDailyRate().multiply(BigDecimal.valueOf(days)));
        rental.setStatus(RentalStatus.ACTIVE);

        car.setStatus(CarStatus.RENTED);
        carRepository.save(car);

        return rentalRepository.save(rental);
    }

    private void validateRentalDates(Rental rental) {
        if (rental.getStartDate().isBefore(LocalDate.now())) {
            throw new RuntimeException("Start date cannot be in the past");
        }

        if (rental.getEndDate().isBefore(rental.getStartDate())) {
            throw new RuntimeException("End date must be after start date");
        }

        if (ChronoUnit.DAYS.between(rental.getStartDate(), rental.getEndDate()) > 30) {
            throw new RuntimeException("Maximum rental period is 30 days");
        }
    }

    private void validateCarAvailability(Car car, LocalDate startDate, LocalDate endDate) {
        if (car.getStatus() != CarStatus.AVAILABLE) {
            throw new RuntimeException("Car with plate " + car.getPlateNumber() + " is currently rented");
        }

        List<Rental> overlappingRentals = rentalRepository
                .findByCarAndStatusAndEndDateAfterAndStartDateBefore(
                        car,
                        RentalStatus.ACTIVE,
                        startDate,
                        endDate
                );

        if (!overlappingRentals.isEmpty()) {
            Rental conflict = overlappingRentals.get(0);
            String message = String.format(
                    "Car is not available from %s to %s because it's already rented from %s to %s",
                    startDate,
                    endDate,
                    conflict.getStartDate(),
                    conflict.getEndDate()
            );
            throw new RuntimeException(message);
        }
    }

    public List<Rental> getAllRentals() {
        return rentalRepository.findAll();
    }

    public Rental getRentalById(Long id) {
        return rentalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rental not found with ID: " + id));
    }

    public Rental updateRental(Long id, Rental rentalDetails) {
        Rental rental = getRentalById(id);

        // Validate new dates if they're being changed
        if (!rental.getStartDate().equals(rentalDetails.getStartDate()) ||
                !rental.getEndDate().equals(rentalDetails.getEndDate())) {
            validateRentalDates(rentalDetails);
            validateCarAvailability(rental.getCar(), rentalDetails.getStartDate(), rentalDetails.getEndDate());

            // Recalculate price if dates changed
            long days = rentalDetails.getEndDate().toEpochDay() - rentalDetails.getStartDate().toEpochDay();
            rentalDetails.setTotalPrice(rental.getCar().getDailyRate().multiply(BigDecimal.valueOf(days)));
        }

        rental.setStartDate(rentalDetails.getStartDate());
        rental.setEndDate(rentalDetails.getEndDate());
        rental.setTotalPrice(rentalDetails.getTotalPrice());
        rental.setStatus(rentalDetails.getStatus());

        return rentalRepository.save(rental);
    }

    public void deleteRental(Long id) {
        Rental rental = getRentalById(id);
        Car car = rental.getCar();
        car.setStatus(CarStatus.AVAILABLE);
        carRepository.save(car);
        rentalRepository.deleteById(id);
    }
}