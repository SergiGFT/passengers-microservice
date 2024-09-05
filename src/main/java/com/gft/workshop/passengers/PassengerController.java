package com.gft.workshop.passengers;

import com.gft.workshop.passengers.model.Passenger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.config.EnableWebFlux;
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
                .map(PassengerController::buildOkResponse);
    }

    @GetMapping("/passenger/{id}")
    public Mono<ResponseEntity<Passenger>> findPassengerById(@PathVariable String id) {

        return passengerService.findPassenger(id)
                .map(PassengerController::buildOkResponse);
    }

    private static ResponseEntity<Passenger> buildOkResponse(Passenger drivingRoute) {
        return new ResponseEntity<>(drivingRoute, HttpStatusCode.valueOf(200));
    }
}
