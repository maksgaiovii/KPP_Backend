package software.kosiv.pizzaflow.model.dish;

import software.kosiv.pizzaflow.model.cook.Cook;
import software.kosiv.pizzaflow.model.event.DishPreparationEventPublisher;
import software.kosiv.pizzaflow.model.order.OrderItem;

import java.util.UUID;

public abstract class Dish implements DishPreparationEventPublisher {
    private final UUID id;
    private final OrderItem orderItem;
    protected boolean isCompleted;
    
    public Dish(OrderItem orderItem){
        this.orderItem = orderItem;
        this.id = orderItem.getId();
    }
    
    public abstract DishState toNextState(Cook cook);
    
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
