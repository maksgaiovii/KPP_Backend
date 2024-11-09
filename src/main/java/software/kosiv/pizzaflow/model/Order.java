package software.kosiv.pizzaflow.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Order {
    private final UUID id = UUID.randomUUID();
    private final LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime completedAt;
    private final List<OrderItem> orderItems;
    
    public Order() {
        orderItems = new ArrayList<>();
    }
    
    public void addOrderItem(MenuItem menuItem) {
        orderItems.add(new OrderItem(this, menuItem));
    }
    
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
    
    public LocalDateTime getCompletedAt() {
        return completedAt;
    }
    
    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }
}
