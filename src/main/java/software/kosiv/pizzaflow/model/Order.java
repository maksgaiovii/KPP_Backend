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
    private final List<OrderItem> uncompletedOrderItems;
    
    public Order(List<MenuItem> menuItems) {
        this.orderItems = menuItems.stream()
                                              .map(m -> new OrderItem(this, m))
                                              .toList();
        this.uncompletedOrderItems = new ArrayList<>(this.orderItems);
    }
    
    protected void completeOrderItem(OrderItem orderItem) {
        if (!orderItems.contains(orderItem)) {
            throw new IllegalArgumentException();
        }
        uncompletedOrderItems.remove(orderItem);
        if (uncompletedOrderItems.isEmpty()){
            completeOrder();
        }
    }
    
    private void completeOrder() {
        setCompletedAt(LocalDateTime.now());
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
    
    private void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }
    
    
}
