package com.gft.workshop.passengers.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Passenger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer passengerId;
    private String name;
    private String email;
    private String phone;
    private String preferredPaymentMethod;
    private ZonedDateTime registeredAt;

    @OneToMany(mappedBy="passenger", cascade = CascadeType.ALL)
    private List<Trip> trips;
}
