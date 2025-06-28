package com.example.car_rental_system.Services;

import com.example.car_rental_system.Models.Customer;
import com.example.car_rental_system.Repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    public Customer createCustomer(Customer customer) {
        if (customerRepository.existsByEmail(customer.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        return customerRepository.save(customer);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
    }

    public Customer updateCustomer(Long id, Customer customerDetails) {
        Customer customer = getCustomerById(id);
        customer.setFirstName(customerDetails.getFirstName());
        customer.setLastName(customerDetails.getLastName());
        customer.setEmail(customerDetails.getEmail());
        customer.setPhone(customerDetails.getPhone());
        customer.setAddress(customerDetails.getAddress());
        customer.setDriverLicense(customerDetails.getDriverLicense());
        return customerRepository.save(customer);
    }

    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }
    public List<Customer> searchByFirstName(String firstName) {
        return customerRepository.findAll().stream()
                .filter(c -> c.getFirstName().equalsIgnoreCase(firstName))
                .toList();
    }

    public List<Customer> searchByLastName(String lastName) {
        return customerRepository.findAll().stream()
                .filter(c -> c.getLastName().equalsIgnoreCase(lastName))
                .toList();
    }

    public List<Customer> searchByEmail(String email) {
        return customerRepository.findAll().stream()
                .filter(c -> c.getEmail().equalsIgnoreCase(email))
                .toList();
    }
}
