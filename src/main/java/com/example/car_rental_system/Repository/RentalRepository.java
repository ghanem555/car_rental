package com.example.car_rental_system.Repository;

import com.example.car_rental_system.Models.Car;
import com.example.car_rental_system.Models.Rental;
import com.example.car_rental_system.Models.RentalStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {
    List<Rental> findByCarAndStatusAndEndDateAfterAndStartDateBefore(Car car, RentalStatus rentalStatus, LocalDate startDate, LocalDate endDate);

    List<Rental> findByCarPlateNumber(String plateNumber);
    List<Rental> findByCustomerCustomerId(Long customerId);

    boolean existsByCarAndStatusInAndStartDateLessThanEqualAndEndDateGreaterThanEqual(Car car, List<RentalStatus> reserved, LocalDate endDate, LocalDate startDate);

}
