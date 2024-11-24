package software.kosiv.pizzaflow.model;

import software.kosiv.pizzaflow.event.DishPreparationCompletedEvent;
import software.kosiv.pizzaflow.event.DishPreparationStartedEvent;

public interface IDishPreparationEventListener {
    void onDishPreparationStartedEvent(DishPreparationStartedEvent event);
    void onDishPreparationCompletedEvent(DishPreparationCompletedEvent event);
}
