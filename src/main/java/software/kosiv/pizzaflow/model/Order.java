package software.kosiv.pizzaflow.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Order {
    private final UUID id = UUID.randomUUID();
    private final LocalDateTime createdAt = LocalDateTime.now();
    private final List<OrderItem> orderItems;
    private final List<OrderItem> uncompletedOrderItems;
    private final Customer customer;
    private CashRegister cashRegister;
    private LocalDateTime completedAt;
    
    public Order(List<MenuItem> menuItems, Customer customer) {
        this.orderItems = menuItems.stream()
                                              .map(m -> new OrderItem(this, m))
                                              .toList();
        this.customer = customer;
        customer.setOrder(this);
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
    
    @Override
    public String toString() {
        return "Order{" +
                       "id=" + id +
                       ", createdAt=" + createdAt +
                       ", customer=" + customer +
                       ", completedAt=" + completedAt +
                       '}';
    }
    
    public Customer getCustomer() {
        return customer;
    }
    
    public CashRegister getCashRegister() {
        return cashRegister;
    }
    
    public void setCashRegister(CashRegister cashRegister) {
        if(this.cashRegister != null) {
            throw new IllegalStateException(); // fixme: change exception class
        }
        this.cashRegister = cashRegister;
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

    public List<OrderItem> getUncompletedOrderItems() {
        return uncompletedOrderItems;
    }

    private void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }
}
