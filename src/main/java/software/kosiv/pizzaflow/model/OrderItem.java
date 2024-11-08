package software.kosiv.pizzaflow.model;

import java.util.UUID;

public class OrderItem {
    private final UUID id = UUID.randomUUID();
    private final MenuItem menuItem;
    
    public OrderItem(MenuItem menuItem) {
        this.menuItem = menuItem;
    }
    
    public UUID getId() {
        return id;
    }
    
    public MenuItem getMenuItem() {
        return menuItem;
    }
}
