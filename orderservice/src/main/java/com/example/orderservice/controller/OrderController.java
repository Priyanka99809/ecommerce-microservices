package com.example.orderservice.controller;

import com.example.orderservice.model.Order;
import com.example.orderservice.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public List<Order> getOrders() {
        return orderService.getAllOrders();
    }

    @PostMapping
    public String createOrder(@RequestBody Order order) {
        //check if the user in order request actually exists or not
        boolean validation = orderService.validateUser(order.getUserid());
        //check if this product actually exists in product service
        boolean validate_product= orderService.validateProduct(order.getProductId());
        if(! validation)
        {
            return "Invalid User, can't create order!!";
        }
        if(!validate_product)
        {
            return "Invalid Product, can't create order, choose another product to order!";
        }
        else
        {
            orderService.createOrder(order);
            return "Order with UserId"+ order.getUserid()+" and product Id: "+ order.getProductId()+" created successfully!!";
        }
    }
}