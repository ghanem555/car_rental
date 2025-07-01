package com.example.car_rental_system.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "car")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Car {
    @Id
    @Column(name = "plate_number")
    private String plateNumber;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private String model;

    @Column(name = "daily_rate", nullable = false, precision = 10, scale = 2)
    private BigDecimal dailyRate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CarStatus status;

    @Column(name = "last_maintenance")
    private LocalDate lastMaintenance;

    @ManyToOne
    @JoinColumn(name = "type_name", nullable = false)
    @JsonBackReference
    private CarType carType;

    @OneToMany(mappedBy = "car", fetch = FetchType.LAZY)
    private List<Rental> rentals;

    @OneToMany(mappedBy = "car")
    private List<Maintenance> maintenanceRecords = new ArrayList<>();

}
