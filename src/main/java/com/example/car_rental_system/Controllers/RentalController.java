package com.example.car_rental_system.Controllers;
import com.example.car_rental_system.DTO.RentalRequsetDto;
import com.example.car_rental_system.DTO.RentalResponseDto;
import com.example.car_rental_system.Models.Car;
import com.example.car_rental_system.Models.CarStatus;
import com.example.car_rental_system.Models.Rental;
import com.example.car_rental_system.Services.RentalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/rentals")
public class RentalController {
    private final RentalService rentalService;

    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @PostMapping
    public ResponseEntity<RentalResponseDto> createRental( @RequestBody RentalRequsetDto rentalRequsetDto) {
        RentalResponseDto response = rentalService.createRental(rentalRequsetDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @GetMapping
    public ResponseEntity<List<RentalResponseDto>> getAllRentals() {
        List<RentalResponseDto> rentals = rentalService.getAllRentals();
        return ResponseEntity.ok(rentals);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RentalResponseDto> getRentalById(@PathVariable Long id) {
        RentalResponseDto rental = rentalService.getRentalById(id);
        return ResponseEntity.ok(rental);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RentalResponseDto> updateRental(
            @PathVariable Long id,
             @RequestBody RentalRequsetDto rentalRequsetDto) {
        RentalResponseDto updatedRental = rentalService.updateRental(id, rentalRequsetDto);
        return ResponseEntity.ok(updatedRental);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRental(@PathVariable Long id) {
        rentalService.deleteRental(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/search/plateNumber")
    public ResponseEntity<List<RentalResponseDto>> searchByPlateNumber(@RequestParam String plateNumber) {
        List<RentalResponseDto> rentals = rentalService.searchByPlateNumber(plateNumber);
        return ResponseEntity.ok(rentals);
    }

    @GetMapping("/by-customer/{customerId}")
    public ResponseEntity<List<RentalResponseDto>> getRentalsByCustomerId(
            @PathVariable Long customerId) {
        List<RentalResponseDto> rentals = rentalService.getRentalsByCustomerId(customerId);
        return ResponseEntity.ok(rentals);
    }
    
}
