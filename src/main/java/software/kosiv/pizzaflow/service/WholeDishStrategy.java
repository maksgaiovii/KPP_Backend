package software.kosiv.pizzaflow.service;

import software.kosiv.pizzaflow.event.DishPreparationCompletedEvent;
import software.kosiv.pizzaflow.event.DishPreparationStartedEvent;
import software.kosiv.pizzaflow.event.EventManager;
import software.kosiv.pizzaflow.model.Cook;
import software.kosiv.pizzaflow.model.Dish;
import software.kosiv.pizzaflow.model.DishState;

import java.util.concurrent.atomic.AtomicBoolean;

public class WholeDishStrategy implements ICookStrategy {
    private final AtomicBoolean isPaused = new AtomicBoolean(false);
    private final EventManager<DishPreparationStartedEvent> startEventManager;
    private final EventManager<DishPreparationCompletedEvent> completedEventManager;
    private Cook cook;

    public WholeDishStrategy(EventManager<DishPreparationStartedEvent> startEventManager,
                             EventManager<DishPreparationCompletedEvent> completedEventManager) {
        this.startEventManager = startEventManager;
        this.completedEventManager = completedEventManager;
    }

    @Override
    public DishState prepareDish(Dish dish) {
        while (!dish.isCompleted() && !isPaused.get()) {
            startEventManager.publishEvent(new DishPreparationStartedEvent(this, dish, dish.getNextState(), cook));
            dish.toNextState();
            completedEventManager.publishEvent(new DishPreparationCompletedEvent(this, dish, dish.getState(), cook));
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
}
