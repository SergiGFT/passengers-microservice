package com.gft.workshop.passengers;

import com.gft.workshop.passengers.model.Passenger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.ZonedDateTime;
import java.util.UUID;

@Service
public class PassengerService {

    private final PassengerRepository passengerRepository;

    private final CustomPassengerRepository customPassengerRepository;


    @Autowired
    public PassengerService(PassengerRepository passengerRepository, CustomPassengerRepository customPassengerRepository) {
        this.passengerRepository = passengerRepository;
        this.customPassengerRepository = customPassengerRepository;
    }

    public Mono<Passenger> createPassenger(Passenger passenger) {
        passenger.setRegisteredAt(ZonedDateTime.now());
        return this.customPassengerRepository.insertPassenger(passenger);
    }

    public Mono<Passenger> findPassenger(String passengerId) {
        return passengerRepository.findById(passengerId);
    }


}
