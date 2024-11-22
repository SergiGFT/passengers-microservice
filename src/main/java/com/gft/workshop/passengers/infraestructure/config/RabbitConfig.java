package com.gft.workshop.passengers.infraestructure.config;

import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitConfig {
/**
    public static final String QUEUE_NAME = "vehicleLocationQueue";
    public static final String EXCHANGE_NAME = "vehicleLocationExchange";
    public static final String ROUTING_KEY = "passenger.routing.key";

    @Bean
    public Queue queue() {
        return new Queue(QUEUE_NAME, false);
    }

    @Bean
    public Exchange exchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Sender sender() {
        return RabbitSender.create(); // Crea el sender reactivo
    }

    @Bean
    public Receiver receiver() {
        return RabbitFlux.createReceiver(); // Crea el receiver reactivo
    }

**/

}
