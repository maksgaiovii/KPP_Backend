package software.kosiv.pizzaflow.model;

import java.util.UUID;

public abstract class Dish {
    private final UUID uuid;
    private final OrderItem orderItem;
    protected boolean isCompleted;
    
    public Dish(OrderItem orderItem){
        this.orderItem = orderItem;
        this.uuid = orderItem.getId();
    }
    
    public abstract DishState toNextState();
    
    public UUID getUuid() {
        return uuid;
    }
    
    public OrderItem getOrderItem() {
        return orderItem;
    }
    
    public abstract DishState getNextState();
    
    public abstract DishState getState();
    
    public boolean isCompleted() {
        return isCompleted;
    }
    
    @Override
    public String toString() {
        return "Dish{" +
                       "uuid=" + uuid +
                       '}';
    }
}
