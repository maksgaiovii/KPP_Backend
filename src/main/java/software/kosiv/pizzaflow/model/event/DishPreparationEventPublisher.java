package software.kosiv.pizzaflow.model.event;

import software.kosiv.pizzaflow.event.DishPreparationCompletedEvent;
import software.kosiv.pizzaflow.event.DishPreparationStartedEvent;

public interface DishPreparationEventPublisher {
    void subscribe(DishPreparationEventListener listener);
    
    void unsubscribe(DishPreparationEventListener listener);

    void notifyPreparationStart(DishPreparationStartedEvent event);
    void notifyPreparationComplete(DishPreparationCompletedEvent event);
}
