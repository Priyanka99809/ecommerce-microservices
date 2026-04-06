package com.example.orderservice.service;

import com.example.orderservice.model.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    //adding rest template for user verification
    private final RestTemplate restTemplate;
    private final HandlerExceptionResolver handlerExceptionResolver;

    public OrderService(RestTemplate restTemplate, HandlerExceptionResolver handlerExceptionResolver)
    {
        this.restTemplate= restTemplate;
        this.handlerExceptionResolver = handlerExceptionResolver;
    }

    private List<Order> orders = new ArrayList<>();
    private Long idCounter = 1L;

    public List<Order> getAllOrders() {
        return orders;
    }

    public boolean validateUser(Long userId) {
        //we will validate user by calling http request to orders api - http://localhost:8082/users
        String url = "http://user-service:8082/users/" + userId;
        try {
            System.out.println("Calling URL: " + url);

            Object user = restTemplate.getForObject(url, Object.class);

            System.out.println("SUCCESS RESPONSE: " + user);

            return user != null;

        } catch (Exception e) {
            System.out.println("FULL ERROR:");
            e.printStackTrace();
            return false;
        }
    }

    public void createOrder(Order order) {
        order.setId(idCounter++);
        orders.add(order);
    }
}