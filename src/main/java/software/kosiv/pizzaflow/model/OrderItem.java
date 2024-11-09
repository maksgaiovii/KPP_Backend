package software.kosiv.pizzaflow.model;

import java.util.UUID;

public class OrderItem {
    private final UUID id = UUID.randomUUID();
    private final Order order;
    private final MenuItem menuItem;
    
    public OrderItem(Order order, MenuItem menuItem) {
        this.order = order;
        this.menuItem = menuItem;
    }
    
    public Order getOrder() {
        return order;
    }
    
    public UUID getId() {
        return id;
    }
    
    public MenuItem getMenuItem() {
        return menuItem;
    }
}
