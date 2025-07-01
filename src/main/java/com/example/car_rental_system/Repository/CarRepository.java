package com.example.car_rental_system.Repository;

import com.example.car_rental_system.Models.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;

@Repository
public interface CarRepository extends JpaRepository<Car, String> {

    Arrays findByBrandIgnoreCase(String brand);
}
