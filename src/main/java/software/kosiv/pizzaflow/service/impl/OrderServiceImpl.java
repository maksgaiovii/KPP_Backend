package software.kosiv.pizzaflow.service.impl;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import software.kosiv.pizzaflow.model.order.Order;
import software.kosiv.pizzaflow.service.ICashRegisterService;
import software.kosiv.pizzaflow.service.ICookService;
import software.kosiv.pizzaflow.service.IOrderService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class OrderServiceImpl implements IOrderService {
    private final ICookService cookService;
    private final ICashRegisterService cashRegisterService;
    private final List<Order> activeOrders = new ArrayList<>();
    
    public OrderServiceImpl(ICookService cookService, ICashRegisterService cashRegisterService) {
        this.cookService = cookService;
        this.cashRegisterService = cashRegisterService;
    }
    
    @Override
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
    
    @Override
    public List<Order> getActiveOrders() {
        return activeOrders;
    }
}
