package com.example.orderservice.model;
import com.example.orderservice.repository.OrderRepository;

@Entity
@Table(name="Order")
public class Order {
    private Long id;
    private Long userId;
    private Long productId;

    public Order() {}

    public Order(Long id, Long userId, Long productId) {
        this.id = id;
        this.userId = userId;
        this.productId = productId;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserid() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }
}