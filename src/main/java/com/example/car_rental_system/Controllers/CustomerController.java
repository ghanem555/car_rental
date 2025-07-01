package com.example.car_rental_system.Controllers;

import com.example.car_rental_system.DTO.CustomerRequsetDto;
import com.example.car_rental_system.DTO.CustomerResponseDto;
import com.example.car_rental_system.Models.Customer;
import com.example.car_rental_system.Services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    @Autowired
    private  CustomerService customerService;


    @PostMapping
    public ResponseEntity<CustomerResponseDto> createCustomer( @RequestBody CustomerRequsetDto customerRequestDTO) {
        CustomerResponseDto response = customerService.createCustomer(customerRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponseDto>> getAllCustomers() {
        List<CustomerResponseDto> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponseDto> getCustomerById(@PathVariable Long id) {
        CustomerResponseDto customer = customerService.getCustomerById(id);
        return ResponseEntity.ok(customer);
    }


    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponseDto> updateCustomer(
            @PathVariable Long id,
             @RequestBody CustomerRequsetDto customerRequestDTO) {
        CustomerResponseDto updatedCustomer = customerService.updateCustomer(id, customerRequestDTO);
        return ResponseEntity.ok(updatedCustomer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }
//    @GetMapping("/search/first-name")
//    public List<Customer> searchByFirstName(@RequestParam String firstName) {
//        return customerService.searchByFirstName(firstName);
//    }
//
//    @GetMapping("/search/last-name")
//    public List<Customer> searchByLastName(@RequestParam String lastName) {
//        return customerService.searchByLastName(lastName);
//    }
//
//    @GetMapping("/search/email")
//    public List<Customer> searchByEmail(@RequestParam String email) {
//        return customerService.searchByEmail(email);
//    }

}
