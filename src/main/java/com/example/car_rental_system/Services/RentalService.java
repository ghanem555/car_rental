package com.example.car_rental_system.Services;

import com.example.car_rental_system.DTO.RentalRequsetDto;
import com.example.car_rental_system.DTO.RentalResponseDto;
import com.example.car_rental_system.Mapper.RentalMapper;
import com.example.car_rental_system.Models.*;
import com.example.car_rental_system.Repository.CarRepository;
import com.example.car_rental_system.Repository.CustomerRepository;
import com.example.car_rental_system.Repository.RentalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RentalService {
    private final RentalRepository rentalRepository;
    private final CustomerRepository customerRepository;
    private final CarRepository carRepository;
    private final RentalMapper rentalMapper;

    public RentalResponseDto createRental(RentalRequsetDto rentalRequsetDto) {
        // Validate customer exists
        Customer customer = customerRepository.findById(rentalRequsetDto.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found with ID: " + rentalRequsetDto.getCustomerId()));

        // Validate car exists
        Car car = carRepository.findById(rentalRequsetDto.getPlateNumber())
                .orElseThrow(() -> new RuntimeException("Car not found with plate: " + rentalRequsetDto.getPlateNumber()));

        // Validate rental dates
        validateRentalDates(rentalRequsetDto);

        // Check car availability
        validateCarAvailability(car, rentalRequsetDto.getStartDate(), rentalRequsetDto.getEndDate());

        // Create rental entity
        Rental rental = rentalMapper.toEntity(rentalRequsetDto, customer, car);

        // Calculate price
        long days = ChronoUnit.DAYS.between(rental.getStartDate(), rental.getEndDate());
        rental.setTotalPrice(car.getDailyRate().multiply(BigDecimal.valueOf(days)));
        rental.setStatus(RentalStatus.ACTIVE);

        // Update car status
        car.setStatus(CarStatus.RENTED);
        carRepository.save(car);

        Rental savedRental = rentalRepository.save(rental);
        return rentalMapper.toDto(savedRental);
    }

    public List<RentalResponseDto> getAllRentals() {
        return rentalRepository.findAll().stream()
                .map(rentalMapper::toDto)
                .collect(Collectors.toList());
    }

    public RentalResponseDto getRentalById(Long id) {
        Rental rental = rentalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rental not found with ID: " + id));
        return rentalMapper.toDto(rental);
    }

    public RentalResponseDto updateRental(Long id, RentalRequsetDto rentalRequsetDto) {
        Rental rental = rentalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rental not found with ID: " + id));

        Customer customer = customerRepository.findById(rentalRequsetDto.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found with ID: " + rentalRequsetDto.getCustomerId()));

        Car car = carRepository.findById(rentalRequsetDto.getPlateNumber())
                .orElseThrow(() -> new RuntimeException("Car not found with plate: " + rentalRequsetDto.getPlateNumber()));

        // Validate dates if they're being changed
        if (!rental.getStartDate().equals(rentalRequsetDto.getStartDate()) ||
                !rental.getEndDate().equals(rentalRequsetDto.getEndDate())) {
            validateRentalDates(rentalRequsetDto);
            validateCarAvailability(car, rentalRequsetDto.getStartDate(), rentalRequsetDto.getEndDate());
        }

        // Update rental details
        rental.setCustomer(customer);
        rental.setCar(car);
        rental.setStartDate(rentalRequsetDto.getStartDate());
        rental.setEndDate(rentalRequsetDto.getEndDate());

        // Recalculate price if dates changed
        long days = ChronoUnit.DAYS.between(rentalRequsetDto.getStartDate(), rentalRequsetDto.getEndDate());
        rental.setTotalPrice(car.getDailyRate().multiply(BigDecimal.valueOf(days)));

        Rental updatedRental = rentalRepository.save(rental);
        return rentalMapper.toDto(updatedRental);
    }

    public void deleteRental(Long id) {
        Rental rental = rentalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rental not found with ID: " + id));

        // Return car to available status
        Car car = rental.getCar();
        car.setStatus(CarStatus.AVAILABLE);
        carRepository.save(car);

        rentalRepository.deleteById(id);
    }

    public List<RentalResponseDto> searchByPlateNumber(String plateNumber) {
        return rentalRepository.findByCarPlateNumber(plateNumber).stream()
                .map(rentalMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<RentalResponseDto> getRentalsByCustomerId(Long customerId) {
        // First verify customer exists
        if (!customerRepository.existsById(customerId)) {
            throw new RuntimeException("Customer not found with ID: " + customerId);
        }

        return rentalRepository.findByCustomerCustomerId(customerId).stream()
                .map(rentalMapper::toDto)
                .collect(Collectors.toList());
    }


    private void validateRentalDates(RentalRequsetDto dto) {
        if (dto.getStartDate().isBefore(LocalDate.now())) {
            throw new RuntimeException("Start date cannot be in the past");
        }

        if (dto.getEndDate().isBefore(dto.getStartDate())) {
            throw new RuntimeException("End date must be after start date");
        }

        if (ChronoUnit.DAYS.between(dto.getStartDate(), dto.getEndDate()) > 30) {
            throw new RuntimeException("Maximum rental period is 30 days");
        }
    }

//    private void validateCarAvailability(Car car, LocalDate startDate, LocalDate endDate) {
//        List<Rental> rental = rentalRepository.findByCarPlateNumber(car.getPlateNumber());
//
//        rental.stream()
//
//        if (car.getStatus() != CarStatus.AVAILABLE) {
//            throw new RuntimeException("Car is not available for rental");
//        }
//
//        boolean hasConflict = rentalRepository.existsByCarAndStatusInAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
//                car,
//                List.of(RentalStatus.RESERVED, RentalStatus.ACTIVE),
//                endDate,
//                startDate);
//
//        if (hasConflict) {
//            throw new RuntimeException("Car is already rented during the requested period");
//        }
//    }

    private void validateCarAvailability(Car car, LocalDate startDate, LocalDate endDate) {
        // Get all rentals for this car
        List<Rental> existingRentals = rentalRepository.findByCarPlateNumber(car.getPlateNumber());

        // Check for date conflicts
        boolean hasConflict = existingRentals.stream()
                .anyMatch(rental ->
                        (startDate.isBefore(rental.getEndDate()) &&
                                endDate.isAfter(rental.getStartDate()))
                );

        if (hasConflict) {
            throw new RuntimeException("Car is already rented between " + startDate + " and " + endDate);
        }
    }
}