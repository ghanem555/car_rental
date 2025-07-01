package com.example.car_rental_system.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponseDto {
    private Long customerId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String driverLicense;
    private String address;
    private List<Long> rentalIds;
}
