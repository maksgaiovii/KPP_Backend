package software.kosiv.pizzaflow.model;

import software.kosiv.pizzaflow.event.DishPreparationCompletedEvent;
import software.kosiv.pizzaflow.event.DishPreparationStartedEvent;
import software.kosiv.pizzaflow.event.EventManager;

public class WholeDishStrategy implements ICookStrategy {
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
        while (!dish.isCompleted() && cook.getStatus() != CookStatus.PAUSED) {
            startEventManager.publishEvent(new DishPreparationStartedEvent(this, dish, dish.getNextState(), cook));
            dish.toNextState();
            completedEventManager.publishEvent(new DishPreparationCompletedEvent(this, dish, dish.getState(), cook));
        }

        return dish.getState();
    }

    @Override
    public void setCook(Cook cook) {
        this.cook = cook;
    }

    @Override
    public ICookStrategy clone() {
        return new WholeDishStrategy(startEventManager, completedEventManager);
    }
}
