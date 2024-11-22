package com.gft.workshop.passengers.application.controller;

import com.gft.workshop.passengers.application.service.PassengerService;
import com.gft.workshop.passengers.domain.model.Passenger;
import com.gft.workshop.passengers.domain.model.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.List;

@RestController
public class PassengerController {

    private final PassengerService passengerService;

    @Autowired
    public PassengerController(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    @PostMapping("/passenger")
    public Mono<ResponseEntity<Passenger>> createPassenger(@RequestBody Passenger passenger) {

        return passengerService.createPassenger(passenger)
                .map(pass -> new ResponseEntity<>(pass, HttpStatus.CREATED))
                .onErrorReturn(ResponseEntity.badRequest().build())
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/passenger/{id}")
    public Mono<ResponseEntity<Passenger>> findPassengerById(@PathVariable String id) {

        return passengerService.findPassenger(id)
                .map(pass -> new ResponseEntity<>(pass, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/passenger")
    public Mono<ResponseEntity<Flux<Passenger>>> findAllPassengers() {
        Flux<Passenger> passengers = passengerService.findAllPassengers();

        return passengers.hasElements()
                .flatMap(hasElements ->
                    hasElements ? Mono.just(ResponseEntity.ok(passengers)) :
                            Mono.just(ResponseEntity.notFound().build())
                );
    }

    @PostMapping("/trip/passenger/{id}")
    public Mono<ResponseEntity<Trip>> createTripForPassenger(@PathVariable String id, @RequestBody Trip trip) {

        return passengerService.addTripToPassenger(id, trip)
                .map(pass -> new ResponseEntity<>(pass, HttpStatus.CREATED))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.BAD_REQUEST))
                .onErrorReturn(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @GetMapping("/trip/passenger/{id}")
    public Mono<ResponseEntity<Flux<Trip>>> getTripsByPassenger(@PathVariable String id) {
        Flux<Trip> trips = passengerService.getTripsByPassengerId(id);

        return trips.hasElements()
                .flatMap(hasElements ->
                    hasElements ? Mono.just(ResponseEntity.ok(trips)) :
                        Mono.just(ResponseEntity.notFound().build())
                );
    }

    @PutMapping("trip/{tripId}")
    public Mono<ResponseEntity<String>> markTripAsCompleted(@PathVariable String tripId) {

        return passengerService.markTripAsCompleted(tripId)
                .map(nTrips->
                        nTrips.equals(0L) ?
                                new ResponseEntity<>("no trips found", HttpStatus.NOT_FOUND) :
                                new ResponseEntity<>(nTrips + " trips updated", HttpStatus.OK));

    }

    @GetMapping("passenger/{startDate}/{endDate}")
    public Mono<ResponseEntity<Flux<Passenger>>> getPassengersWithTripsInDateRange(@PathVariable LocalDate startDate,
                                                                                   @PathVariable LocalDate endDate) {
        Flux<Passenger> passengers = passengerService.findPassengersWithTripsInDateRange(startDate, endDate);

        return passengers.hasElements()
                .flatMap(hasElements ->
                        hasElements ? Mono.just(ResponseEntity.ok(passengers)) :
                                Mono.just(ResponseEntity.notFound().build()));
    }

}
