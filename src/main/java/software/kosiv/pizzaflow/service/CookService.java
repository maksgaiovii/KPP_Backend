package software.kosiv.pizzaflow.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import software.kosiv.pizzaflow.event.DishPreparationCompletedEvent;
import software.kosiv.pizzaflow.event.DishPreparationStartedEvent;
import software.kosiv.pizzaflow.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;

import static java.util.Comparator.comparing;
import static software.kosiv.pizzaflow.generator.CookGenerator.generateCook;

@Service
public class CookService {
    private final ApplicationEventPublisher eventPublisher;
    private final Queue<OrderItem> orderItemQueue;
    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    private List<Cook> cooks = new ArrayList<>();
    private final Logger logger = LoggerFactory.getLogger(CookService.class);
    
    public CookService(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
        this.orderItemQueue = new PriorityBlockingQueue<>(1000,
                                                          (comparing(o -> o.getOrder().getCreatedAt())));
    }
    
    public void acceptOrder(Order order) {
        orderItemQueue.addAll(order.getOrderItems());
    }
    
    private void assignOrderItemToCook(Cook cook, OrderItem orderItem) {
        cook.setBusy();
        Dish dish = orderItem.getDish();
        while (!dish.isCompleted()){
            publishDishPreparationStartEvent(cook, dish);
            var dishState = cook.completeDish(dish); // todo: send every dish status, not only completed
            publishDishPreparationCompletedEvent(cook, dish, dishState);
            
        }
        cook.setFree();
    }
    
    private void completeOrderItem(Cook cook, OrderItem orderItem) {
        logger.info("Cook {} start cooking order item {} from order {}",
                    cook.getName(),
                    orderItem.getMenuItem().getName(),
                    orderItem.getOrder().getId());
        executorService.execute(() -> assignOrderItemToCook(cook, orderItem));
    }
    
    private void publishDishPreparationStartEvent(Cook cook, Dish dish) {
        var event = new DishPreparationStartedEvent(this, dish, dish.getNextState(), cook);
        eventPublisher.publishEvent(event);
    }
    
    private void publishDishPreparationCompletedEvent(Cook cook, Dish dish, DishState dishState) {
        var event = new DishPreparationCompletedEvent(this, dish, dishState, cook);
        eventPublisher.publishEvent(event);
    }
    
    private List<Cook> findAllAvailableCooks() {
        return cooks.stream()
                    .filter(Cook::isAvailable)
                    .toList();
    }
    
    private Cook findAvailableCook() {
        return cooks.stream()
                    .filter(Cook::isAvailable)
                    .findFirst()
                    .orElse(null);
    }
    
    @Scheduled(cron = "0/1 * * * * *")
    public void checkWaitingQueue() {
        if (orderItemQueue.isEmpty()) {
            return;
        }
        List<Cook> availableCooks = findAllAvailableCooks();
        for (var cook : availableCooks) {
            OrderItem orderItem = orderItemQueue.poll();
            if (orderItem != null) {
                completeOrderItem(cook, orderItem);
            }
        }
    }
    
    public void setCookCount(int count) {
        this.cooks = List.copyOf(generateCook(count));
        this.executorService = Executors.newFixedThreadPool(count);
    }
}
