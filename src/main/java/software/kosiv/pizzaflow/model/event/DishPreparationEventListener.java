package software.kosiv.pizzaflow.model.event;

import software.kosiv.pizzaflow.event.DishPreparationCompletedEvent;
import software.kosiv.pizzaflow.event.DishPreparationStartedEvent;

public interface DishPreparationEventListener {
    void onDishPreparationStartedEvent(DishPreparationStartedEvent event);
    void onDishPreparationCompletedEvent(DishPreparationCompletedEvent event);
}
