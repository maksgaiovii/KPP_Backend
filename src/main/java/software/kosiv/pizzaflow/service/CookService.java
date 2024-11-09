package software.kosiv.pizzaflow.service;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import software.kosiv.pizzaflow.event.DishPreparationCompletedEvent;
import software.kosiv.pizzaflow.event.DishPreparationStartedEvent;
import software.kosiv.pizzaflow.model.Cook;
import software.kosiv.pizzaflow.model.Dish;
import software.kosiv.pizzaflow.model.DishState;
import software.kosiv.pizzaflow.model.OrderItem;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
public class CookService {
    private ApplicationEventPublisher applicationEventPublisher;
    private List<Cook> chefs;
    private final Queue<OrderItem> waitingQueue = new ConcurrentLinkedQueue<>();
    
    public CookService() {
    
    }
    
    public void acceptOrderItem(OrderItem orderItem) {
        Cook availableCook = findAvailableCook();
        assignOrderItemToCook(availableCook, orderItem);
    }
    
    private void assignOrderItemToCook(Cook cook, OrderItem orderItem) {
        if (cook != null) {
            cook.setBusy();
            Dish dish = orderItem.getMenuItem().createDish();
            publishDishPreparationStartEvent(cook, dish);
            var dishState = cook.prepareDish(dish);
            publishDishPreparationCompletedEvent(cook, dish, dishState);
        } else {
            waitingQueue.add(orderItem);
        }
    }
    
    private void publishDishPreparationStartEvent(Cook cook, Dish dish) {
        var event = new DishPreparationStartedEvent(this, dish, dish.getNextState(), cook);
        applicationEventPublisher.publishEvent(event);
    }
    
    private void publishDishPreparationCompletedEvent(Cook cook, Dish dish, DishState dishState) {
        var event = new DishPreparationCompletedEvent(this, dish, dishState, cook);
        applicationEventPublisher.publishEvent(event);
    }
    
    private Cook findAvailableCook() {
        return chefs.stream()
                    .filter(Cook::isAvailable)
                    .findFirst()
                    .orElse(null);
    }
    
    @Scheduled(cron = "0/1 * * * * *")
    public void checkWaitingQueue() {
        if (waitingQueue.isEmpty()) {
            return;
        }
        Cook availableCook = findAvailableCook();
        if (availableCook != null) {
            OrderItem orderItem = waitingQueue.poll();
            if (orderItem != null) {
                acceptOrderItem(orderItem);
            }
        }
    }
}
