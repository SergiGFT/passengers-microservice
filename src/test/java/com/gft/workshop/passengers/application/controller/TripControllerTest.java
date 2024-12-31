package com.gft.workshop.passengers.application.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import com.gft.workshop.passengers.application.dto.TripDTO;
import com.gft.workshop.passengers.application.service.TripService;
import com.gft.workshop.passengers.domain.model.Trip;
import java.time.LocalDateTime;
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
class TripControllerTest {

  @InjectMocks private TripController tripController;

  @Mock private TripService tripService;

  private Trip trip;
  private TripDTO tripDTO;
  private List<Trip> trips;

  @BeforeEach
  void setUp() {
    trip = Trip.builder().tripId(1L).routeId(1).build();
    tripDTO = TripDTO.builder().routeId(1).build();
    var trip2 =
        Trip.builder()
            .tripId(2L)
            .routeId(2L)
            .passengerId(1L)
            .startTime(LocalDateTime.parse("2024-11-02T00:00:00"))
            .endTime(LocalDateTime.parse("2024-11-02T00:00:00"))
            .build();

    trips = List.of(trip, trip2);
  }

  @Test
  void testCreateTripForPassenger() {

    var passengerId = 1L;

    when(tripService.addTripToPassenger(eq(passengerId), any(TripDTO.class)))
        .thenReturn(Mono.just(trip));

    Mono<Trip> result = tripController.createTripForPassenger(passengerId, tripDTO);

    StepVerifier.create(result).expectNext(trip).verifyComplete();
  }

  @Test
  void testGetTripHistoricalByPassengerId() {

    var passengerId = 1L;

    when(tripService.getTripsByPassengerId(passengerId)).thenReturn(Flux.fromIterable(trips));
    Flux<Trip> result = tripController.getTripsByPassenger(passengerId);

    StepVerifier.create(result).expectNextSequence(trips).verifyComplete();
  }

  @Test
  void testGetTripHistoricalByPassengerIdNotFound() {

    var passengerId = 1L;

    when(tripService.getTripsByPassengerId(passengerId)).thenReturn(Flux.empty());
    Flux<Trip> result = tripController.getTripsByPassenger(passengerId);

    StepVerifier.create(result).expectNext().verifyComplete();
  }
}
