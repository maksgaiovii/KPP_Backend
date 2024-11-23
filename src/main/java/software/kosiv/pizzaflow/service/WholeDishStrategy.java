package software.kosiv.pizzaflow.service;

import org.springframework.context.ApplicationEventPublisher;
import software.kosiv.pizzaflow.event.DishPreparationCompletedEvent;
import software.kosiv.pizzaflow.event.DishPreparationStartedEvent;
import software.kosiv.pizzaflow.model.Cook;
import software.kosiv.pizzaflow.model.Dish;
import software.kosiv.pizzaflow.model.DishState;

import java.util.concurrent.atomic.AtomicBoolean;

public class WholeDishStrategy implements ICookStrategy {
    private final AtomicBoolean isPaused = new AtomicBoolean(false);
    private final ApplicationEventPublisher eventPublisher;
    private Cook cook;

    public WholeDishStrategy(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @Override
    public DishState prepareDish(Dish dish) {
        while (!dish.isCompleted() && !isPaused.get()) {
            publishDishPreparationStartEvent(cook, dish);
            dish.toNextState();
            publishDishPreparationCompletedEvent(cook, dish, dish.getState());
        }

        return dish.getState();
    }

    @Override
    public void setPaused() {
        isPaused.set(true);
    }

    @Override
    public void setFree() {
        isPaused.set(false);
    }

    @Override
    public void setCook(Cook cook) {
        this.cook = cook;
    }

    private void publishDishPreparationStartEvent(Cook cook, Dish dish) {
        var event = new DishPreparationStartedEvent(this, dish, dish.getNextState(), cook);
        eventPublisher.publishEvent(event);
    }

    private void publishDishPreparationCompletedEvent(Cook cook, Dish dish, DishState dishState) {
        var event = new DishPreparationCompletedEvent(this, dish, dishState, cook);
        eventPublisher.publishEvent(event);
    }
}
