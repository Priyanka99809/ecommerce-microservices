package com.example.orderservice.service;

import com.example.orderservice.model.Order;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    //adding rest template for user verification
    private final WebClient webClient;

    public OrderService(WebClient webClient)
    {
        this.webClient= webClient;
    }

    private List<Order> orders = new ArrayList<>();
    private Long idCounter = 1L;
    @Value("${product.service.url}")
    //fetching value from environment variable in docker or application.properties
    private String productServiceUrl;
    @Value("${user.service.url}")
    private String userServiceUrl;

    public List<Order> getAllOrders() {
        return orders;
    }

    public boolean validateUser(Long userId) {
        //we will validate user by calling http request to orders api - http://localhost:8082/users
        String url = userServiceUrl + "/users/"+userId;
        try {
            System.out.println("Calling URL: " + url);

            Object user= webClient.get().
                    uri(url)
                    .retrieve()
                    .bodyToMono(Object.class)
                    .block();
            System.out.println("SUCCESS RESPONSE: " + user);

            return user!= null;

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

    public boolean validateProduct(Long productId) {
        String url= productServiceUrl +"/products/"+productId;
        try {
            System.out.println("Calling URL: " + url);

            Object product= webClient.get().
                    uri(url)
                    .retrieve()
                    .bodyToMono(Object.class)
                    .block();
            System.out.println("SUCCESS RESPONSE: " + product);

            return product!= null;

        } catch (Exception e) {
            System.out.println("FULL ERROR:");
            e.printStackTrace();
            return false;
        }
    }
}