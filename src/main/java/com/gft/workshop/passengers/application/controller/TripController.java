package com.gft.workshop.passengers.application.controller;

import com.gft.workshop.passengers.application.dto.TripDTO;
import com.gft.workshop.passengers.application.service.TripService;
import com.gft.workshop.passengers.domain.model.Trip;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("trip")
public class TripController {

  private final TripService tripService;

  @PostMapping("/passenger/{id}")
  public Mono<Trip> createTripForPassenger(@PathVariable String id, @RequestBody TripDTO trip) {
    return tripService.addTripToPassenger(id, trip);
  }

  @GetMapping("/passenger/{id}")
  public Flux<Trip> getTripsByPassenger(@PathVariable String id) {
    return tripService.getTripsByPassengerId(id);
  }

  @PutMapping("/{tripId}")
  public Mono<ResponseEntity<String>> markTripAsCompleted(@PathVariable String tripId) {

    return tripService
        .markTripAsCompleted(tripId)
        .map(
            nTrips ->
                nTrips.equals(0L)
                    ? new ResponseEntity<>("no trips found", HttpStatus.NOT_FOUND)
                    : new ResponseEntity<>(nTrips + " trips updated", HttpStatus.OK));
  }
}
