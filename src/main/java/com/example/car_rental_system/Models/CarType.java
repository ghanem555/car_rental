package com.example.car_rental_system.Models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "car_type")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CarType {
    @Id
    @Column(name = "type_name")
    private String typeName;

    @Column(columnDefinition = "TEXT")
    private String description;

    @OneToMany(mappedBy = "carType")
    @JsonManagedReference
    private List<Car> cars = new ArrayList<>();

}
