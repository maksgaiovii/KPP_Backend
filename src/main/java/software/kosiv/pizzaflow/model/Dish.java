package software.kosiv.pizzaflow.model;

import java.util.UUID;

public abstract class Dish {
    private final UUID id;
    protected boolean isCompleted;
    
    public Dish(){
        this.id = UUID.randomUUID();
    }
    
    public UUID getId() {
        return id;
    }
    
    public abstract DishState getNextState();
    
    public abstract DishState toNextState();
    
    public abstract DishState getState();
    
    public boolean isCompleted() {
        return isCompleted;
    }
}
