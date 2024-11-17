package software.kosiv.pizzaflow.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import software.kosiv.pizzaflow.event.DishPreparationCompletedEvent;
import software.kosiv.pizzaflow.event.DishPreparationStartedEvent;
import software.kosiv.pizzaflow.model.*;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

import static software.kosiv.pizzaflow.generator.CookGenerator.generateCook;

@Service
public class CookService {
    private final ApplicationEventPublisher eventPublisher;
    private final ScheduledExecutorService scheduledExecutorService;
    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    private final Deque<Order> orderQueue;
    private List<Cook> cooks = new ArrayList<>();

    private final Logger logger = LoggerFactory.getLogger(CookService.class);
    private final AtomicBoolean isPaused = new AtomicBoolean(false);

    public CookService(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
        this.orderQueue = new LinkedBlockingDeque<>(1000);
        this.scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(this::checkWaitingQueue, 0, 1, TimeUnit.SECONDS);
    }

    public void acceptOrder(Order order) {
        orderQueue.add(order);
    }

    public void setCookCount(int count) {
        this.cooks = List.copyOf(generateCook(count));
        this.executorService = Executors.newFixedThreadPool(count);
    }

    public void stop() {
        isPaused.set(true);
    }

    public void resume() {
        isPaused.set(false);
    }

    public void terminate() {
        scheduledExecutorService.shutdownNow();
        executorService.shutdownNow();
    }

    private void assignOrderItemToCook(Cook cook, OrderItem orderItem) {
        cook.setBusy();
        Dish dish = orderItem.getDish();
        while (!dish.isCompleted()){
            publishDishPreparationStartEvent(cook, dish);
            var dishState = cook.prepareDish(dish);
            publishDishPreparationCompletedEvent(cook, dish, dishState);
            
        }
        cook.setFree();
    }
    
    private void completeOrderItem(Cook cook, OrderItem orderItem) {
        logger.info("Cook {} start cooking order item {} with id {} from order {}",
                    cook.getName(),
                    orderItem.getMenuItem().getName(),
                    orderItem.getId(),
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

    private void checkWaitingQueue() {
        if (orderQueue.isEmpty() || isPaused.get()) {
            return;
        }

        List<Cook> availableCooks = findAllAvailableCooks();
        processOrders(availableCooks);
    }

    private void processOrders(List<Cook> availableCooks) {
        for (Cook cook : availableCooks) {
            Order order = orderQueue.poll();

            if (order == null) {
                break;
            }

            processOrder(cook, order);
        }
    }

    private void processOrder(Cook cook, Order order) {
        var orderItems = order.getUncompletedOrderItems();

        for (OrderItem orderItem : orderItems) {
            if (orderItem.tryLockForPreparation()) {
                completeOrderItem(cook, orderItem);
                break;
            }
        }

        if (order.getCompletedAt() == null) {
            orderQueue.add(order);
        }
    }
}
