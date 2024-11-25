package software.kosiv.pizzaflow.service.impl;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import software.kosiv.pizzaflow.event.CookChangeStateEvent;
import software.kosiv.pizzaflow.event.DishPreparationCompletedEvent;
import software.kosiv.pizzaflow.event.DishPreparationStartedEvent;
import software.kosiv.pizzaflow.model.cook.Cook;
import software.kosiv.pizzaflow.model.cook.CookStatus;
import software.kosiv.pizzaflow.model.cook.CookStrategy;
import software.kosiv.pizzaflow.model.dish.Pizza;
import software.kosiv.pizzaflow.model.event.DishPreparationEventListener;
import software.kosiv.pizzaflow.model.order.Order;
import software.kosiv.pizzaflow.model.order.OrderItem;
import software.kosiv.pizzaflow.service.CookService;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

import static software.kosiv.pizzaflow.generator.CookGenerator.generate;

@Service
public class CookServiceImpl implements DishPreparationEventListener, CookService {
    private final ApplicationEventPublisher eventPublisher;

    private final ScheduledExecutorService scheduledExecutorService;
    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final AtomicBoolean isPaused = new AtomicBoolean(false);

    private final Deque<Order> orderDeque;
    private List<Cook> cooks = new ArrayList<>();
    
    public CookServiceImpl(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
        this.orderDeque = new LinkedBlockingDeque<>(1000);
        this.scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(this::checkWaitingQueue, 0, 1, TimeUnit.SECONDS);
    }
    
    @Override
    public void acceptOrder(Order order) {
        order.getOrderItems().forEach(orderItem -> orderItem.getDish().subscribe(this));
        orderDeque.add(order);
    }
    
    @Override
    public void stop() {
        isPaused.set(true);
        cooks.forEach(Cook::setPaused);
    }
    
    @Override
    public void resume() {
        isPaused.set(false);
        cooks.forEach(Cook::setFree);
    }
    
    @Override
    public void terminate() {
        stop();
        scheduledExecutorService.shutdown();
        executorService.shutdown();
    }
    
    @Override
    public void stopCook(UUID id) {
        Cook cook = findCookById(id);
        if (cook == null) {
            return;
        }

        CookStatus previousStatus = cook.getStatus();
        cook.setPaused();
        eventPublisher.publishEvent(new CookChangeStateEvent(this, cook, previousStatus));
    }
    
    @Override
    public void resumeCook(UUID id) {
        Cook cook = findCookById(id);
        if (cook != null) {
            cook.setFree();
            eventPublisher.publishEvent(new CookChangeStateEvent(this, cook, CookStatus.PAUSED));
        }
    }
    
    @Override
    public void setCookCount(int count) {
        this.cooks = List.copyOf(generate(count, Arrays.asList(Pizza.PizzaState.values())));
        this.executorService = Executors.newFixedThreadPool(count);
    }
    
    @Override
    public void setCookStrategy(CookStrategy strategy) {
        for (Cook cook : cooks) {
            cook.setStrategy(strategy.clone());
        }
    }

    @Override
    public void onDishPreparationStartedEvent(DishPreparationStartedEvent event) {
        eventPublisher.publishEvent(event);
    }

    @Override
    public void onDishPreparationCompletedEvent(DishPreparationCompletedEvent event) {
        eventPublisher.publishEvent(event);
    }

    private Cook findCookById(UUID id) {
        return cooks.stream()
                    .filter(cook -> cook.getId().equals(id))
                    .findFirst()
                    .orElse(null);
    }

    private void checkWaitingQueue() {
        if (orderDeque.isEmpty() || isPaused.get()) {
            return;
        }

        List<Cook> availableCooks = findAllAvailableCooks();
        processOrders(availableCooks);
    }

    private void processOrders(List<Cook> availableCooks) {
        for (Cook cook : availableCooks) {
            Order order = findAvailibleOrder(cook);

            if (order != null) {
                processOrder(cook, order);
            }
        }
    }

    private void processOrder(Cook cook, Order order) {
        var orderItem = order.getUncompletedOrderItems().stream()
                             .filter(orderItem1 -> cook.canCook(orderItem1.getDish()))
                             .findFirst()
                             .orElseThrow();

        orderDeque.remove(order);
        processOrderItem(cook, orderItem);
    }

    private void processOrderItem(Cook cook, OrderItem orderItem) {
        if(orderItem.tryLockForPreparation()) {
            executorService.execute(() ->
            {
                assignOrderItemToCook(cook, orderItem);
                Order order = orderItem.getOrder();
                if (order.getCompletedAt() == null) {
                    orderDeque.add(orderItem.getOrder());
                }
                else {
                    order.getOrderItems().forEach(item -> item.getDish().unsubscribe(this));
                }
            });
        }
    }

    private void assignOrderItemToCook(Cook cook, OrderItem orderItem) {
        cook.setBusy();
        eventPublisher.publishEvent(new CookChangeStateEvent(this, cook, CookStatus.FREE));
        cook.prepareDish(orderItem);
        cook.setFree();
        eventPublisher.publishEvent(new CookChangeStateEvent(this, cook, CookStatus.BUSY));
    }

    private List<Cook> findAllAvailableCooks() {
        return cooks.stream()
                    .filter(Cook::isAvailable)
                    .toList();
    }

    private Order findAvailibleOrder(Cook cook) {
        return orderDeque.stream()
                         .filter(order -> order.getUncompletedOrderItems().stream()
                                               .anyMatch(orderItem -> cook.canCook(orderItem.getDish())
                                                                      && !orderItem.isBeingPrepared()))
                         .findFirst()
                         .orElse(null);
    }
}
