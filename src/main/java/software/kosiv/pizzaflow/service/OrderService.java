package software.kosiv.pizzaflow.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import software.kosiv.pizzaflow.model.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class OrderService {
    private final CookService cookService;
    private final CashRegisterService cashRegisterService;
    private final List<Order> activeOrders = new ArrayList<>();
    
    public OrderService(CookService cookService, CashRegisterService cashRegisterService) {
        this.cookService = cookService;
        this.cashRegisterService = cashRegisterService;
    }
    
    public void processOrder(Order order) {
        activeOrders.add(order);
        cookService.acceptOrder(order);
    }
    
    @Scheduled(fixedDelay = 3, timeUnit = TimeUnit.SECONDS)
    public void checkForCompletedOrders() { // fixme: maybe there is better way to handle completed orders
        List<Order> completedOrders = new ArrayList<>();
        for (int i = 0, activeOrdersSize = activeOrders.size(); i < activeOrdersSize; i++) {
            Order order = activeOrders.get(i);
            if (order.getCompletedAt() != null) {
                completedOrders.add(order);
                cashRegisterService.closeOrder(order);
            }
        }
        activeOrders.removeAll(completedOrders);
    }
    
    public List<Order> getActiveOrders() {
        return activeOrders;
    }
}
