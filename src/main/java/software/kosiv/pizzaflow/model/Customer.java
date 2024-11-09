package software.kosiv.pizzaflow.model;

import java.util.UUID;

public class Customer {
    private final UUID id = UUID.randomUUID();
    private final String name;
    private Order order;
    
    public Customer(String name) {
        this.name = name;
    }
    
    public Customer(String name, Order order) {
        this.name = name;
        this.order = order;
    }
    
    public UUID getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public Order getOrder() {
        return order;
    }
    
    public void setOrder(Order order) {
        this.order = order;
    }
}
