package software.kosiv.pizzaflow.model;

import java.util.UUID;

public class OrderItem {
    private final UUID id = UUID.randomUUID();
    private final Order order;
    private final MenuItem menuItem;
    private Dish dish;
    
    public OrderItem(Order order, MenuItem menuItem) {
        this.order = order;
        this.menuItem = menuItem;
    }
    
    protected void complete() {
        order.completeOrderItem(this);
    }
    
    public Order getOrder() {
        return order;
    }
    
    public UUID getId() {
        return id;
    }
    
    public Dish getDish() {
        if(dish == null) {
            this.dish = menuItem.createDish(this);
        }
        return dish;
    }
    
    public MenuItem getMenuItem() {
        return menuItem;
    }
}
