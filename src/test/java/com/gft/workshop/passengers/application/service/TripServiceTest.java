package com.gft.workshop.passengers.application.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.gft.workshop.passengers.application.dto.TripDTO;
import com.gft.workshop.passengers.domain.model.Passenger;
import com.gft.workshop.passengers.domain.model.Trip;
import com.gft.workshop.passengers.domain.repository.PassengerRepository;
import com.gft.workshop.passengers.domain.repository.TripRepository;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class TripServiceTest {

  @InjectMocks private TripService tripService;

  @Mock private PassengerRepository passengerRepository;

  @Mock private TripRepository tripRepository;

  private Passenger passenger;
  private Trip trip;
  private TripDTO tripDTO;
  private List<Trip> trips;
  private String passengerId;

  @BeforeEach
  void setUp() {

    passengerId = "P1";

    passenger =
        Passenger.builder().passengerId(passengerId).name("John").email("john@example.com").build();

    trip = Trip.builder().routeId("1").startStop("First stop").passengerId(passengerId).build();
    tripDTO =
        TripDTO.builder().routeId("1").startStop("First stop").passengerId(passengerId).build();

    trips = new ArrayList<>();
    trips.add(trip);
    trips.add(Trip.builder().tripId("T2").routeId("R2").build());
  }

  @Test
  void addTripToPassenger() {
    when(passengerRepository.findById(any(String.class))).thenReturn(Mono.just(passenger));
    when(tripRepository.save(any(Trip.class))).thenAnswer(inv -> Mono.just(inv.getArgument(0)));

    Mono<Trip> result = tripService.addTripToPassenger(passengerId, tripDTO);

    StepVerifier.create(result)
        .expectNextMatches(savedTrip -> savedTrip.getPassengerId().equals(passengerId))
        .verifyComplete();

    verify(passengerRepository, times(1)).findById(passengerId);
    verify(tripRepository, times(1)).save(trip);
  }

  @Test
  void getTripsByPassengerId() {
    when(tripRepository.findAllByPassengerId(passengerId)).thenReturn(Flux.fromIterable(trips));

    Flux<Trip> result = tripService.getTripsByPassengerId(passengerId);

    StepVerifier.create(result).expectNextSequence(trips);
  }
}
