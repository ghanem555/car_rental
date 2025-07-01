package com.example.car_rental_system.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRequsetDto {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String driverLicense;
    private String address;
}
