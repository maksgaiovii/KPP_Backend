package software.kosiv.pizzaflow.service;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import software.kosiv.pizzaflow.event.OrderCompletedEvent;
import software.kosiv.pizzaflow.model.MenuItem;
import software.kosiv.pizzaflow.model.Order;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    private final MenuService menuService;
    private final CookService cookService;
    private final ApplicationEventPublisher eventPublisher;
    private final List<Order> activeOrders = new ArrayList<>();
    
    public OrderService(MenuService menuService, CookService cookService, ApplicationEventPublisher eventPublisher) {
        this.menuService = menuService;
        this.cookService = cookService;
        this.eventPublisher = eventPublisher;
    }
    
    public Order createOrder(List<MenuItem> selectedItems) {
        Order order = new Order(selectedItems);
        processOrder(order);
        return order;
    }
    
    public void processOrder(Order order) {
        activeOrders.add(order);
        cookService.acceptOrder(order);
    }
    
    @Scheduled(cron = "0/1 * * * * *")
    public void checkForCompletedOrders() { // fixme: maybe there is better way to handle completed orders
        List<Order> completedOrders = new ArrayList<>();
        for (Order order : activeOrders) {
            if (order.getCompletedAt() != null) {
                completedOrders.add(order);
                eventPublisher.publishEvent(new OrderCompletedEvent(this, order));
            }
        }
        activeOrders.removeAll(completedOrders);
    }
    
    public List<Order> getActiveOrders() {
        return activeOrders;
    }
}
