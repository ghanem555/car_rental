package com.example.car_rental_system.DTO;

import com.example.car_rental_system.Models.RentalStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RentalResponseDto {
    private Long rentalId;
    private Long customerId;
    private String customerName;
    private String plateNumber;
    private String carInfo;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal totalPrice;
    private RentalStatus status;
    private List<Long> paymentIds;

}
