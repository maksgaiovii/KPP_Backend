package software.kosiv.pizzaflow.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class Order {
    private final UUID id = UUID.randomUUID();
    private final LocalDateTime createdAt = LocalDateTime.now();
    private final List<OrderItem> orderItems;
    
    public Order(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
    
    public UUID getId() {
        return id;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public List<OrderItem> getOrderItems() {
        return orderItems;
    }
}
