package com.gft.workshop.passengers.application;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.gft.workshop.passengers.application.controller.PassengerController;
import com.gft.workshop.passengers.application.dto.PassengerDTO;
import com.gft.workshop.passengers.application.service.PassengerService;
import com.gft.workshop.passengers.domain.model.Passenger;
import java.time.LocalDate;
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
class PassengerControllerTest {

  @InjectMocks private PassengerController passengerController;

  @Mock private PassengerService passengerService;

  private Passenger passenger;
  private PassengerDTO passengerDTO;
  private List<Passenger> passengers;

  @BeforeEach
  void setUp() {
    passengerDTO = PassengerDTO.builder().name("John").email("john@example.com").build();
    passenger =
        Passenger.builder().passengerId("P1").name("John").email("john@example.com").build();

    passengers = new ArrayList<>();
    passengers.add(passenger);
  }

  @Test
  void testCreatePassenger() {

    when(passengerService.createPassenger(any(PassengerDTO.class)))
        .thenReturn(Mono.just(passenger));

    Mono<Passenger> result = passengerController.createPassenger(passengerDTO);

    StepVerifier.create(result).expectNext(passenger).verifyComplete();
  }

  @Test
  void testCreateInvalidPassenger() {

    when(passengerService.createPassenger(any(PassengerDTO.class))).thenReturn(Mono.empty());

    Mono<Passenger> result = passengerController.createPassenger(passengerDTO);

    StepVerifier.create(result).verifyComplete();
  }

  @Test
  void testFindPassengerById() {

    when(passengerService.findPassenger(any())).thenReturn(Mono.just(passenger));

    Mono<Passenger> result = passengerController.findPassengerById(String.valueOf(1));

    StepVerifier.create(result).expectNext(passenger).verifyComplete();
  }

  @Test
  void testGetAllPassengers() {

    when(passengerService.findAllPassengers()).thenReturn(Flux.fromIterable(passengers));

    Flux<Passenger> result = passengerController.findAllPassengers();

    StepVerifier.create(result).expectNextSequence(passengers).verifyComplete();
  }

  @Test
  void testGetPassengersWithTripsInDateRange() {

    LocalDate startDate = LocalDate.parse("2024-01-01");
    LocalDate endDate = LocalDate.parse("2024-12-01");

    when(passengerService.findPassengersWithTripsInDateRange(startDate, endDate))
        .thenReturn(Flux.just(passenger));

    Flux<Passenger> result =
        passengerController.getPassengersWithTripsInDateRange(startDate, endDate);

    StepVerifier.create(result).expectNextSequence(List.of(passenger)).verifyComplete();
  }
}
