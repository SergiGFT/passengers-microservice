package com.gft.workshop.passengers;

import com.gft.workshop.passengers.model.Passenger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class PassengerService {

    private final PassengerRepository passengerRepository;


    @Autowired
    public PassengerService(PassengerRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
    }

    public Mono<Passenger> createPassenger(Passenger passenger) {
        passenger.setPassengerId(UUID.randomUUID());
        return passengerRepository.save(passenger);
    }
}
