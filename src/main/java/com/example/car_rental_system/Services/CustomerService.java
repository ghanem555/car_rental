package com.example.car_rental_system.Services;

import com.example.car_rental_system.DTO.CustomerRequsetDto;
import com.example.car_rental_system.DTO.CustomerResponseDto;
import com.example.car_rental_system.Mapper.CustomerMapper;
import com.example.car_rental_system.Models.Customer;
import com.example.car_rental_system.Repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerResponseDto createCustomer(CustomerRequsetDto customerRequestDTO) {
        if (customerRepository.existsByEmail(customerRequestDTO.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        if (customerRepository.existsByDriverLicense(customerRequestDTO.getDriverLicense())) {
            throw new RuntimeException("Driver license already exists");
        }

        Customer customer = customerMapper.toEntity(customerRequestDTO);
        Customer savedCustomer = customerRepository.save(customer);
        return customerMapper.toDto(savedCustomer);
    }

    public List<CustomerResponseDto> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(customerMapper::toDto)
                .collect(Collectors.toList());
    }


    public CustomerResponseDto getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with ID: " + id));
        return customerMapper.toDto(customer);
    }

    public CustomerResponseDto updateCustomer(Long id, CustomerRequsetDto customerRequestDTO) {
        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with ID: " + id));

        // Check if email is being changed to an existing one
        if (!existingCustomer.getEmail().equals(customerRequestDTO.getEmail()) &&
                customerRepository.existsByEmail(customerRequestDTO.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        // Check if driver license is being changed to an existing one
        if (!existingCustomer.getDriverLicense().equals(customerRequestDTO.getDriverLicense()) &&
                customerRepository.existsByDriverLicense(customerRequestDTO.getDriverLicense())) {
            throw new RuntimeException("Driver license already exists");
        }

        // Update customer fields
        existingCustomer.setFirstName(customerRequestDTO.getFirstName());
        existingCustomer.setLastName(customerRequestDTO.getLastName());
        existingCustomer.setEmail(customerRequestDTO.getEmail());
        existingCustomer.setPhone(customerRequestDTO.getPhone());
        existingCustomer.setDriverLicense(customerRequestDTO.getDriverLicense());
        existingCustomer.setAddress(customerRequestDTO.getAddress());

        Customer updatedCustomer = customerRepository.save(existingCustomer);
        return customerMapper.toDto(updatedCustomer);
    }

    public void deleteCustomer(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new RuntimeException("Customer not found with ID: " + id);
        }
        customerRepository.deleteById(id);
    }
//    public List<Customer> searchByFirstName(String firstName) {
//        return customerRepository.findAll().stream()
//                .filter(c -> c.getFirstName().equalsIgnoreCase(firstName))
//                .toList();
//    }
//
//    public List<Customer> searchByLastName(String lastName) {
//        return customerRepository.findAll().stream()
//                .filter(c -> c.getLastName().equalsIgnoreCase(lastName))
//                .toList();
//    }
//
//    public List<Customer> searchByEmail(String email) {
//        return customerRepository.findAll().stream()
//                .filter(c -> c.getEmail().equalsIgnoreCase(email))
//                .toList();
//    }
}
