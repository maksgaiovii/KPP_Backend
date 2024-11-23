package software.kosiv.pizzaflow.service;

import software.kosiv.pizzaflow.event.DishPreparationCompletedEvent;
import software.kosiv.pizzaflow.event.DishPreparationStartedEvent;
import software.kosiv.pizzaflow.event.EventManager;
import software.kosiv.pizzaflow.model.Cook;
import software.kosiv.pizzaflow.model.Dish;
import software.kosiv.pizzaflow.model.DishState;

import java.util.concurrent.atomic.AtomicBoolean;

public class OneStepStrategy implements ICookStrategy {
    private final AtomicBoolean isPaused = new AtomicBoolean(false);
    private final EventManager<DishPreparationStartedEvent> startEventManager;
    private final EventManager<DishPreparationCompletedEvent> completedEventManager;
    private Cook cook;

    public OneStepStrategy(
            EventManager<DishPreparationStartedEvent> startEventManager,
            EventManager<DishPreparationCompletedEvent> completedEventManager
    ) {
        this.startEventManager = startEventManager;
        this.completedEventManager = completedEventManager;
    }

    @Override
    public DishState prepareDish(Dish dish) {
        if (isPaused.get()) {
            return dish.getState();
        }

        startEventManager.publishEvent(new DishPreparationStartedEvent(this, dish, dish.getNextState(), cook));
        dish.toNextState();
        completedEventManager.publishEvent(new DishPreparationCompletedEvent(this, dish, dish.getState(), cook));

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
