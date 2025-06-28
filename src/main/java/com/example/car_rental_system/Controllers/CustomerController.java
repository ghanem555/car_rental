package com.example.car_rental_system.Controllers;

import com.example.car_rental_system.Models.Customer;
import com.example.car_rental_system.Services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    @Autowired
    private  CustomerService customerService;


    @PostMapping
    public Customer createCustomer(@RequestBody Customer customer) {
        return customerService.createCustomer(customer);
    }

    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }


    @GetMapping("/{id}")
    public Customer getCustomer(@PathVariable long id) {
        return customerService.getCustomerById(id);
    }


    @PutMapping("/{id}")
    public Customer updateCustomer(@PathVariable long id, @RequestBody Customer customer) {
        return customerService.updateCustomer(id, customer);
    }

    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable long id) {
        customerService.deleteCustomer(id);
    }

    @GetMapping("/search/first-name")
    public List<Customer> searchByFirstName(@RequestParam String firstName) {
        return customerService.searchByFirstName(firstName);
    }

    @GetMapping("/search/last-name")
    public List<Customer> searchByLastName(@RequestParam String lastName) {
        return customerService.searchByLastName(lastName);
    }

    @GetMapping("/search/email")
    public List<Customer> searchByEmail(@RequestParam String email) {
        return customerService.searchByEmail(email);
    }

}
