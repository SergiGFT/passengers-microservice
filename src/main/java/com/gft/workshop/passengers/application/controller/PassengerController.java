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
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @GetMapping("/passenger/{id}")
    public Mono<ResponseEntity<Passenger>> findPassengerById(@PathVariable String id) {

        return passengerService.findPassenger(id)
                .map(PassengerController::buildOkResponse);
    }

    @GetMapping("/passenger")
    public Flux<ResponseEntity<Passenger>> findAllPassengers() {
        return passengerService.findAllPassengers().map(PassengerController::buildOkResponse);
    }


    @PostMapping("/trips/passenger/{id}")
    public Mono<ResponseEntity<Trip>> createTripForPassenger(@PathVariable String passengerId, @RequestBody Trip trip) {

        return passengerService.createTrip(passengerId, trip)
                .map(PassengerController::buildOkResponse)
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.BAD_REQUEST));

    }


    private static ResponseEntity<Passenger> buildOkResponse(Passenger drivingRoute) {
        return new ResponseEntity<>(drivingRoute, HttpStatusCode.valueOf(200));
    }



    private static ResponseEntity<Trip> buildOkResponse(Trip trip) {
        return new ResponseEntity<>(trip, HttpStatusCode.valueOf(200));
    }

}
