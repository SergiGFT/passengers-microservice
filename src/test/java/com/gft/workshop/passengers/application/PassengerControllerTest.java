package com.gft.workshop.passengers.application;


import com.gft.workshop.passengers.application.controller.PassengerController;
import com.gft.workshop.passengers.application.service.PassengerService;
import com.gft.workshop.passengers.domain.model.Passenger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
public class PassengerControllerTest {

    @InjectMocks
    private PassengerController passengerController;

    @Mock
    private PassengerService passengerService;

    private Passenger passenger;

    @BeforeEach
    public void setUp() {
        passenger = new Passenger();
        passenger.setName("John");
        passenger.setEmail("john@example.com");

    }


    @Test
    public void testCreatePassenger() {

        when(passengerService.createPassenger(any(Passenger.class))).thenReturn(Mono.just(passenger));

        Mono<ResponseEntity<Passenger>> result = passengerController.createPassenger(passenger);

        StepVerifier.create(result)
                .expectNext(ResponseEntity.status(HttpStatus.CREATED).body(passenger))
                .verifyComplete();

    }

    @Test
    public void testCreateInvalidPassenger() {

        when(passengerService.createPassenger(any(Passenger.class))).thenReturn(Mono.empty());

        Mono<ResponseEntity<Passenger>> result = passengerController.createPassenger(passenger);

        StepVerifier.create(result)
                .expectNext(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null))
                .verifyComplete();

    }

}
