package com.alison;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;

@SpringBootConfiguration
@EnableCircuitBreaker
public class CircuitBreakerApplication {
    public static void main(String[] args) {
        SpringApplication.run(CircuitBreakerApplication.class, args);
    }
}
