package com.example.car_rental_system.Controllers;
import com.example.car_rental_system.Models.Rental;
import com.example.car_rental_system.Services.RentalService;
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
    public Rental createRental(@RequestBody Rental rental) {
        return rentalService.createRental(rental);
    }

    @GetMapping
    public List<Rental> getAllRentals() {
        return rentalService.getAllRentals();
    }

    @GetMapping("/{id}")
    public Rental getRentalById(@PathVariable long id) {
        return rentalService.getRentalById(id);
    }

    @PutMapping("/{id}")
    public Rental updateRental(@PathVariable long id, @RequestBody Rental rental) {
        return rentalService.updateRental(id, rental);
    }

    @DeleteMapping("/{id}")
    public void deleteRental(@PathVariable long id) {
        rentalService.deleteRental(id);
    }
    
}
