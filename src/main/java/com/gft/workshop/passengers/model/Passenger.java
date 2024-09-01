package com.gft.workshop.passengers.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.ZonedDateTime;
import java.util.UUID;

@Table("passenger")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Passenger {

    @Id
    private UUID passengerId;
    private String name;
    private String email;
    private String phone;
    private String preferredPaymentMethod;
    private ZonedDateTime registeredAt;

  /*  @OneToMany(mappedBy="passenger", cascade = CascadeType.ALL)
    private List<Trip> trips;*/
}
