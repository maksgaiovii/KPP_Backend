package software.kosiv.pizzaflow.event;


import org.springframework.context.ApplicationEvent;
import software.kosiv.pizzaflow.model.Cook;
import software.kosiv.pizzaflow.model.Dish;
import software.kosiv.pizzaflow.model.DishState;

import java.time.Clock;

public class DishPreparationCompletedEvent extends ApplicationEvent {
    private final Dish dish;
    private final DishState newDishState;
    private final Cook cook;
    
    public DishPreparationCompletedEvent(Object source, Dish dish, DishState newDishState, Cook cook) {
        super(source);
        this.dish = dish;
        this.newDishState = newDishState;
        this.cook = cook;
    }
    
    public DishState getNewDishState() {
        return newDishState;
    }
    
    public Dish getDish() {
        return dish;
    }
    
    public Cook getCook() {
        return cook;
    }
    
    @Override
    public String toString() {
        return "DishPreparationCompletedEvent{" +
                       "dish=" + dish.getUuid() +
                       ", newDishState=" + newDishState +
                       ", cook=" + cook.getName() +
                       '}';
    }
}
