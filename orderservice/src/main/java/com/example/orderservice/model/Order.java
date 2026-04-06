package com.example.orderservice.model;

public class Order {
    private Long id;
    private Long userId;
    private double productId;

    public Order() {}

    public Order(Long id, Long userId, double productId) {
        this.id = id;
        this.userId = userId;
        this.productId = productId;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserid() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public double getProductId() { return productId; }
    public void setProductId(double price) { this.productId = productId; }
}