package com.gft.workshop.passengers.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import reactor.util.annotation.Nullable;

import java.time.ZonedDateTime;

@Table("passenger")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Passenger {

    @Id
    private String passengerId;
    private String name;
    private String email;
    private String phone;
    private String preferredPaymentMethod;
    private ZonedDateTime registeredAt;

}
