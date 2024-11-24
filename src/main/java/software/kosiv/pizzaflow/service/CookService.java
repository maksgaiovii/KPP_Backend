package software.kosiv.pizzaflow.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import software.kosiv.pizzaflow.model.*;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

import static software.kosiv.pizzaflow.generator.CookGenerator.generate;

@Service
public class CookService {
    private final ScheduledExecutorService scheduledExecutorService;
    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    private final Deque<Order> orderQueue;
    private List<Cook> cooks = new ArrayList<>();

    private final Logger logger = LoggerFactory.getLogger(CookService.class);
    private final AtomicBoolean isPaused = new AtomicBoolean(false);

    public CookService() {
        this.orderQueue = new LinkedBlockingDeque<>(1000);
        this.scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(this::checkWaitingQueue, 0, 1, TimeUnit.SECONDS);
    }

    public void acceptOrder(Order order) {
        orderQueue.add(order);
    }

    public void setCookCount(int count) {
        this.cooks = List.copyOf(generate(count, Arrays.asList( Pizza.PizzaState.values())));
        this.executorService = Executors.newFixedThreadPool(count);
    }

    public void stop() {
        isPaused.set(true);
        cooks.forEach(Cook::setPaused);
    }

    public void resume() {
        isPaused.set(false);
        cooks.forEach(Cook::setFree);
    }

    public void terminate() {
        stop();
        scheduledExecutorService.shutdown();
        executorService.shutdown();
    }

    public void stopCook(UUID id) {
        Cook cook = findCookById(id);
        if (cook == null) {
            return;
        }

        cook.setPaused();
    }

    public void resumeCook(UUID id) {
        Cook cook = findCookById(id);
        if (cook != null) {
            cook.setFree();
        }
    }

    public void setCookStrategy(ICookStrategy strategy) {
        for (Cook cook : cooks) {
            cook.setStrategy(strategy.clone());
        }
    }

    private Cook findCookById(UUID id) {
        return cooks.stream()
                    .filter(cook -> cook.getId().equals(id))
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

        orderQueue.remove(order);
        processOrderItem(cook, orderItem);
    }

    private void processOrderItem(Cook cook, OrderItem orderItem) {
        if(orderItem.tryLockForPreparation()) {
            logger.info("Cook {} start cooking order item {} with id {} from order {}",
                    cook.getName(),
                    orderItem.getMenuItem().getName(),
                    orderItem.getId(),
                    orderItem.getOrder().getId());

            executorService.execute(() ->
            {
                assignOrderItemToCook(cook, orderItem);
                if (orderItem.getOrder().getCompletedAt() == null) {
                    orderQueue.add(orderItem.getOrder());
                }
            });
        }
    }

    private void assignOrderItemToCook(Cook cook, OrderItem orderItem) {
        cook.setBusy();
        cook.prepareDish(orderItem);
        cook.setFree();
    }

    private List<Cook> findAllAvailableCooks() {
        return cooks.stream()
                    .filter(Cook::isAvailable)
                    .toList();
    }

    private Order findAvailibleOrder(Cook cook) {
        return orderQueue.stream()
                         .filter(order -> order.getUncompletedOrderItems().stream()
                                               .anyMatch(orderItem -> cook.canCook(orderItem.getDish())
                                                                      && !orderItem.isBeingPrepared()))
                         .findFirst()
                         .orElse(null);
    }
}
