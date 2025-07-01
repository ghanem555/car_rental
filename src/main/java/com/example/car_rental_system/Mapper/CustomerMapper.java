package com.example.car_rental_system.Mapper;

import com.example.car_rental_system.DTO.CustomerRequsetDto;
import com.example.car_rental_system.DTO.CustomerResponseDto;
import com.example.car_rental_system.Models.Customer;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CustomerMapper {
    public Customer toEntity(CustomerRequsetDto dto) {
        Customer customer = new Customer();
        customer.setFirstName(dto.getFirstName());
        customer.setLastName(dto.getLastName());
        customer.setEmail(dto.getEmail());
        customer.setPhone(dto.getPhone());
        customer.setDriverLicense(dto.getDriverLicense());
        customer.setAddress(dto.getAddress());
        return customer;
    }

    public CustomerResponseDto toDto(Customer customer) {
        CustomerResponseDto dto = new CustomerResponseDto();
        dto.setCustomerId(customer.getCustomerId());
        dto.setFirstName(customer.getFirstName());
        dto.setLastName(customer.getLastName());
        dto.setEmail(customer.getEmail());
        dto.setPhone(customer.getPhone());
        dto.setDriverLicense(customer.getDriverLicense());
        dto.setAddress(customer.getAddress());

        if (customer.getRentals() != null) {
            dto.setRentalIds(customer.getRentals().stream()
                    .map(rental -> rental.getRentalId())
                    .collect(Collectors.toList()));
        }

        return dto;
    }
}
