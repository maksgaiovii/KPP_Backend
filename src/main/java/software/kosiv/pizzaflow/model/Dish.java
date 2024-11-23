package software.kosiv.pizzaflow.model;

import java.util.UUID;

public abstract class Dish {
    private final UUID id;
    private final OrderItem orderItem;
    protected boolean isCompleted;
    
    public Dish(OrderItem orderItem){
        this.orderItem = orderItem;
        this.id = orderItem.getId();
    }
    
    public abstract DishState toNextState();
    
    @Override
    public String toString() {
        return "Dish{" +
                       "uuid=" + id +
                       '}';
    }
    
    public OrderItem getOrderItem() {
        return orderItem;
    }
    
    public abstract DishState getNextState();
    
    public abstract DishState getState();
    
    public boolean isCompleted() {
        return isCompleted;
    }
    
    public UUID getId() {
        return id;
    }
}
