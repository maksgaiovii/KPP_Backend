package software.kosiv.pizzaflow.event;

import org.springframework.context.ApplicationEvent;
import software.kosiv.pizzaflow.model.Cook;
import software.kosiv.pizzaflow.model.Dish;
import software.kosiv.pizzaflow.model.DishState;

public class DishPreparationStartedEvent extends ApplicationEvent {
    private final Dish dish;
    private final DishState nextDishState;
    private final Cook cook;
    
    public DishPreparationStartedEvent(Object source, Dish dish, DishState nextDishState, Cook cook) {
        super(source);
        this.dish = dish;
        this.nextDishState = nextDishState;
        this.cook = cook;
    }
    
    public DishState getNextDishState() {
        return nextDishState;
    }
    
    public Dish getDish() {
        return dish;
    }
    
    public Cook getCook() {
        return cook;
    }
    
    @Override
    public String toString() {
        return "DishPreparationStartedEvent{" +
                       "dish=" + dish.getId() +
                       ", nextDishState=" + nextDishState +
                       ", cook=" + cook +
                       '}';
    }
}
