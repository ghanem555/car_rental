package com.example.car_rental_system.DTO;

import com.example.car_rental_system.Models.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponseDto {
    private Long paymentId;
    private Long rentalId;
    private BigDecimal amount;
    private LocalDate paymentDate;
    private PaymentMethod method;
    private String transactionId;
}
