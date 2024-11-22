package com.gft.workshop.passengers.application;


import com.gft.workshop.passengers.application.controller.PassengerController;
import com.gft.workshop.passengers.application.service.PassengerService;
import com.gft.workshop.passengers.domain.model.Passenger;
import com.gft.workshop.passengers.domain.model.Trip;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@ExtendWith(MockitoExtension.class)
class PassengerControllerTest {

    @InjectMocks
    private PassengerController passengerController;

    @Mock
    private PassengerService passengerService;

    private Passenger passenger;
    private Trip trip;
    private List<Trip> trips;
    private List<Passenger> passengers;

    @BeforeEach
    void setUp() {
        passenger = new Passenger();
        passenger.setPassengerId("P1");
        passenger.setName("John");
        passenger.setEmail("john@example.com");

        trip = new Trip();
        trip.setTripId("T1");
        trip.setRouteId(String.valueOf(1));

        trips = new ArrayList<>();
        trips.add(trip);
        trips.add(Trip.builder().tripId("T2").routeId("R2").passengerId("P1")
                        .startTime(LocalDateTime.parse("2024-11-02T00:00:00"))
                .endTime(LocalDateTime.parse("2024-11-02T00:00:00"))
                .build());

        passengers = new ArrayList<>();
        passengers.add(passenger);
    }

    @Test
    void testCreatePassenger() {

        when(passengerService.createPassenger(any(Passenger.class))).thenReturn(Mono.just(passenger));

        Mono<ResponseEntity<Passenger>> result = passengerController.createPassenger(passenger);

        StepVerifier.create(result)
                .expectNext(ResponseEntity.status(HttpStatus.CREATED).body(passenger))
                .verifyComplete();
    }

    @Test
    void testCreateInvalidPassenger() {

        when(passengerService.createPassenger(any(Passenger.class))).thenReturn(Mono.empty());

        Mono<ResponseEntity<Passenger>> result = passengerController.createPassenger(passenger);

        StepVerifier.create(result)
                .expectNext(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null))
                .verifyComplete();
    }

    @Test
    void testFindPassengerById() {

        when(passengerService.findPassenger(any())).thenReturn(Mono.just(passenger));

        Mono<ResponseEntity<Passenger>> result = passengerController.findPassengerById(String.valueOf(1));

        StepVerifier.create(result)
                .expectNext(ResponseEntity.status(HttpStatus.OK).body(passenger)).verifyComplete();

    }

    @Test
    void testCreateTripForPassenger() {

        String passengerId = "P1";

        when(passengerService.addTripToPassenger(eq(passengerId), any(Trip.class))).thenReturn(Mono.just(trip));

        Mono<ResponseEntity<Trip>> result = passengerController.createTripForPassenger(passengerId, trip);

        StepVerifier.create(result)
                .expectNext(ResponseEntity.status(HttpStatus.CREATED).body(trip)).verifyComplete();

    }

    @Test
    void testGetTripHistoricalByPassengerId() {

        String passengerId = "P1";

        when(passengerService.getTripsByPassengerId(passengerId)).thenReturn(Flux.fromIterable(trips));
        Mono<ResponseEntity<Flux<Trip>>> result = passengerController.getTripsByPassenger(passengerId);

        StepVerifier.create(result)
                .assertNext(response-> {
                    assertEquals(HttpStatus.OK, response.getStatusCode());
                    StepVerifier.create(Objects.requireNonNull(response.getBody()))
                                    .expectNextSequence(trips).verifyComplete();
                }).verifyComplete();
    }

    @Test
    void testGetTripHistoricalByPassengerIdNotFound() {

        String passengerId = "P1";

        when(passengerService.getTripsByPassengerId(passengerId))
                .thenReturn(Flux.empty());
        Mono<ResponseEntity<Flux<Trip>>> result = passengerController.getTripsByPassenger(passengerId);

        StepVerifier.create(result)
                .expectNext(ResponseEntity.notFound().build()).verifyComplete();
    }

    @Test
    void testGetAllPassengers() {

        when(passengerService.findAllPassengers()).thenReturn(Flux.fromIterable(passengers));

        Mono<ResponseEntity<Flux<Passenger>>> result = passengerController.findAllPassengers();

        StepVerifier.create(result)
                .assertNext(response-> {
                    assertEquals(HttpStatus.OK, response.getStatusCode());
                    StepVerifier.create(Objects.requireNonNull(response.getBody()))
                            .expectNextSequence(passengers).verifyComplete();
                }).verifyComplete();
    }

    @Test
    void testGetPassengersWithTripsInDateRange() {

        LocalDate startDate = LocalDate.parse("2024-01-01");
        LocalDate endDate = LocalDate.parse("2024-12-01");

        when(passengerService.findPassengersWithTripsInDateRange(startDate, endDate))
                .thenReturn(Flux.just(passenger));

        Mono<ResponseEntity<Flux<Passenger>>> result =
                passengerController.getPassengersWithTripsInDateRange(startDate, endDate);

        StepVerifier.create(result)
                .assertNext(response -> {
                    assertEquals(HttpStatus.OK, response.getStatusCode());
                    StepVerifier.create(Objects.requireNonNull(response.getBody()))
                            .expectNextSequence(List.of(passenger)).verifyComplete();
                }).verifyComplete();

    }
}
