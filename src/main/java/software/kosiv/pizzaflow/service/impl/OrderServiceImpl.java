package software.kosiv.pizzaflow.service.impl;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import software.kosiv.pizzaflow.model.order.Order;
import software.kosiv.pizzaflow.service.CashRegisterService;
import software.kosiv.pizzaflow.service.CookService;
import software.kosiv.pizzaflow.service.OrderService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class OrderServiceImpl implements OrderService {
    private final CookService cookService;
    private final CashRegisterService cashRegisterService;
    private final List<Order> activeOrders = new ArrayList<>();
    
    public OrderServiceImpl(CookService cookService, CashRegisterService cashRegisterService) {
        this.cookService = cookService;
        this.cashRegisterService = cashRegisterService;
    }
    
    @Override
    public void processOrder(Order order) {
        activeOrders.add(order);
        cookService.acceptOrder(order);
    }
    
    @Scheduled(fixedDelay = 3, timeUnit = TimeUnit.SECONDS)
    public void checkForCompletedOrders() {
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
    
    @Override
    public List<Order> getActiveOrders() {
        return activeOrders;
    }
}
