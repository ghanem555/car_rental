package com.example.car_rental_system.Mapper;

import com.example.car_rental_system.DTO.RentalRequsetDto;
import com.example.car_rental_system.DTO.RentalResponseDto;
import com.example.car_rental_system.Models.Car;
import com.example.car_rental_system.Models.Customer;
import com.example.car_rental_system.Models.Rental;
import com.example.car_rental_system.Models.RentalStatus;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;
@Component
public class RentalMapper {

    public Rental toEntity(RentalRequsetDto dto, Customer customer, Car car) {
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setCar(car);
        rental.setStartDate(dto.getStartDate());
        rental.setEndDate(dto.getEndDate());
        rental.setStatus(RentalStatus.RESERVED);
        return rental;
    }

    public RentalResponseDto toDto(Rental rental) {
        RentalResponseDto dto = new RentalResponseDto();
        dto.setRentalId(rental.getRentalId());
        dto.setCustomerId(rental.getCustomer().getCustomerId());
        dto.setCustomerName(rental.getCustomer().getFirstName() + " " + rental.getCustomer().getLastName());
        dto.setPlateNumber(rental.getCar().getPlateNumber());
        dto.setCarInfo(rental.getCar().getBrand() + " " + rental.getCar().getModel());
        dto.setStartDate(rental.getStartDate());
        dto.setEndDate(rental.getEndDate());
        dto.setTotalPrice(rental.getTotalPrice());
        dto.setStatus(rental.getStatus());

        if (rental.getPayments() != null) {
            dto.setPaymentIds(rental.getPayments().stream()
                    .map(payment -> payment.getPaymentId())
                    .collect(Collectors.toList()));
        }

        return dto;
    }

}
