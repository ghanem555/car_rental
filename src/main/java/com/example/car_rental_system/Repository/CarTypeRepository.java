package com.example.car_rental_system.Repository;

import com.example.car_rental_system.Models.CarType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface CarTypeRepository extends JpaRepository<CarType, String> {
    boolean existsById(String typeName);
}
