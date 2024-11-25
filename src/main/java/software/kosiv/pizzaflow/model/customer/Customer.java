package software.kosiv.pizzaflow.model.customer;

import software.kosiv.pizzaflow.model.order.Order;

import java.util.Objects;
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
    
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Customer customer)) {
            return false;
        }
        return Objects.equals(id, customer.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
    
    @Override
    public String toString() {
        return "Customer{" +
                       "id=" + id +
                       ", name='" + name + '\'' +
                       '}';
    }
}
