package com.example.car_rental_system.Services;
import com.example.car_rental_system.Models.CurrencyType;
import com.example.car_rental_system.Models.Payment;
import com.example.car_rental_system.Repository.PaymentRepository;
import com.example.car_rental_system.Repository.RentalRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final RentalRepository rentalRepository;
    private final ExchangeRateClient exchangeRateClient;

    public PaymentService(PaymentRepository paymentRepository,
                          RentalRepository rentalRepository, ExchangeRateClient exchangeRateClient) {
        this.paymentRepository = paymentRepository;
        this.rentalRepository = rentalRepository;

        this.exchangeRateClient = exchangeRateClient;
    }


    public Payment createPayment(Payment payment) {
        rentalRepository.findById(payment.getRental().getRentalId())
                .orElseThrow(() -> new RuntimeException("Rental not found"));

        // Convert to EGP before saving
        BigDecimal rate = exchangeRateClient.getExchangeRate(payment.getCurrency().name(), "EGP");
        BigDecimal amountInEGP = payment.getAmount().multiply(rate);

        payment.setAmount(amountInEGP);
        payment.setCurrency(CurrencyType.EGP); // override currency to EGP after conversion

        return paymentRepository.save(payment);
    }

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public Payment getPaymentById(long id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found with id: " + id));
    }

    public Payment updatePayment(long id, Payment paymentDetails) {
        Payment payment = getPaymentById(id);
        payment.setAmount(paymentDetails.getAmount());
        payment.setPaymentDate(paymentDetails.getPaymentDate());
        payment.setMethod(paymentDetails.getMethod());
        payment.setTransactionId(paymentDetails.getTransactionId());
        return paymentRepository.save(payment);
    }

    public void deletePayment(long id) {
        paymentRepository.deleteById(id);
    }

}
