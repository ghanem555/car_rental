package com.example.car_rental_system.Repository;

import com.example.car_rental_system.Models.Car;
import com.example.car_rental_system.Models.Maintenance;
import com.example.car_rental_system.Models.MaintenanceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public interface MaintenanceRepository extends JpaRepository<Maintenance, Long> {
    int countByCarAndStatusNot(Car car, MaintenanceStatus maintenanceStatus);

    List<Maintenance> findByCar(Car car);
}
