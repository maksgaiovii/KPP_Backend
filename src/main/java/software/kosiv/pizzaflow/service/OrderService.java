package software.kosiv.pizzaflow.service;

import software.kosiv.pizzaflow.model.order.Order;

import java.util.List;

public interface OrderService {
    void processOrder(Order order);
    
    List<Order> getActiveOrders();
}
