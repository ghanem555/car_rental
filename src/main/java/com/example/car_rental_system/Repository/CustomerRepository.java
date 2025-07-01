package com.example.car_rental_system.Repository;
import com.example.car_rental_system.Models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    boolean existsByEmail(String email);


    boolean existsByDriverLicense(String driverLicense);
}

