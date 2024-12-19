package com.gft.workshop.passengers.application;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import com.gft.workshop.passengers.application.dto.PassengerDTO;
import com.gft.workshop.passengers.application.service.PassengerService;
import com.gft.workshop.passengers.domain.model.Passenger;
import com.gft.workshop.passengers.domain.repository.PassengerRepository;
import java.time.Instant;
import java.util.Date;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class PassengerServiceTest {

  @InjectMocks private PassengerService passengerService;

  @Mock private PassengerRepository passengerRepository;

  private PassengerDTO passengerDTO;
  private Passenger passenger;
  private String passengerId;

  @BeforeEach
  void setUp() {
    passengerDTO = PassengerDTO.builder().name("John").email("john@example.com").build();

    passengerId = "P1";

    passenger =
        Passenger.builder().passengerId(passengerId).name("John").email("john@example.com").build();
  }

  @Test
  void createPassengerTest() {

    when(passengerRepository.save(any(Passenger.class)))
        .thenAnswer(inv -> Mono.just(inv.getArgument(0)));

    Mono<Passenger> result = passengerService.createPassenger(passengerDTO);

    StepVerifier.create(result)
        .expectNextMatches(
            savedPassenger -> savedPassenger.getRegisteredAt().equals(Date.from(Instant.now())));
  }

  @Test
  void getPassengerByIdTest() {

    when(passengerRepository.findById(passengerId)).thenReturn(Mono.just(passenger));
    Mono<Passenger> result = passengerService.findPassenger(passengerId);
    StepVerifier.create(result).expectNext(passenger).expectComplete();
  }
}
