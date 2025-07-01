package com.example.car_rental_system.DTO;

import com.example.car_rental_system.Models.CurrencyType;
import com.example.car_rental_system.Models.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequsetDto {
    private Long rentalId;
    private BigDecimal amount;
    private PaymentMethod method;
    private CurrencyType currency;
    private String transactionId;
}
