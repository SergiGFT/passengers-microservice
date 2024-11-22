package com.gft.workshop.passengers.application;

import com.gft.workshop.passengers.application.service.PassengerService;
import com.gft.workshop.passengers.domain.model.Passenger;
import com.gft.workshop.passengers.domain.model.Trip;
import com.gft.workshop.passengers.domain.repository.PassengerRepository;
import com.gft.workshop.passengers.domain.repository.TripRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PassengerServiceTest {

    @InjectMocks
    private PassengerService passengerService;

    @Mock
    private PassengerRepository passengerRepository;

    @Mock
    private TripRepository tripRepository;

    private Passenger passenger;
    private Trip trip;
    private List<Trip> trips;
    private String passengerId;
    private String tripId;

    @BeforeEach
    void setUp() {
        passenger = new Passenger();
        passenger.setPassengerId("P1");
        passenger.setName("John");
        passenger.setEmail("john@example.com");

        passengerId = "P1";

        trip = new Trip();
        trip.setTripId("T1");
        trip.setRouteId(String.valueOf(1));
        trip.setStartStop("First stop");

        trips = new ArrayList<>();
        trips.add(trip);
        trips.add(Trip.builder().tripId("T2").routeId("R2").build());
    }

    @Test
    void createPassengerTest() {

        when(passengerRepository.save(any(Passenger.class)))
                .thenAnswer(inv->Mono.just(inv.getArgument(0)));

        Mono<Passenger> result = passengerService.createPassenger(passenger);

        StepVerifier.create(result).expectNextMatches(savedPassenger-> savedPassenger.getRegisteredAt()
                        .equals(Date.from(Instant.now())));
    }

    @Test
    void createTripToPassengerTestToExistingPassenger() {

        when(passengerRepository.findById(any(String.class))).thenReturn(Mono.just(passenger));
        when(tripRepository.save(any(Trip.class))).thenAnswer(inv->Mono.just(inv.getArgument(0)));

        Mono<Trip> result = passengerService.addTripToPassenger(passengerId, trip);

        StepVerifier.create(result)
                .expectNextMatches(savedTrip->savedTrip.getPassengerId().equals(passengerId))
                .verifyComplete();

        verify(passengerRepository, times(1)).findById(passengerId);
        verify(tripRepository, times(1)).save(trip);
    }


    @Test
    void getTripsByPassengerTest() {

        when(tripRepository.findAllByPassengerId(passengerId))
                .thenReturn(Flux.fromIterable(trips));

        Flux<Trip> result = passengerService.getTripsByPassengerId(passengerId);

        StepVerifier.create(result).expectNextSequence(trips);
    }

    @Test
    void getPassengerByIdTest() {

        when(passengerRepository.findById(passengerId)).thenReturn(Mono.just(passenger));
        Mono<Passenger> result = passengerService.findPassenger(passengerId);
        StepVerifier.create(result).expectNext(passenger);
    }

}
