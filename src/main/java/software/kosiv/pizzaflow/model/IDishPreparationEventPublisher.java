package software.kosiv.pizzaflow.model;

import software.kosiv.pizzaflow.event.DishPreparationCompletedEvent;
import software.kosiv.pizzaflow.event.DishPreparationStartedEvent;

public interface IDishPreparationEventPublisher {
    void subscribe(IDishPreparationEventListener listener);
    void unsubscribe(IDishPreparationEventListener listener);

    void notifyPreparationStart(DishPreparationStartedEvent event);
    void notifyPreparationComplete(DishPreparationCompletedEvent event);
}
