package software.kosiv.pizzaflow.service;

import software.kosiv.pizzaflow.model.MenuItem;
import software.kosiv.pizzaflow.model.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderService {
    private final MenuService menuService;
    private final CookService cookService;
    private List<Order> orders = new ArrayList<>();
    
    public OrderService(MenuService menuService, CookService cookService) {
        this.menuService = menuService;
        this.cookService = cookService;
    }
    
    public Order createOrder(List<MenuItem> selectedItems) {
        Order order = new Order();
        selectedItems.forEach(order::addOrderItem);
        orders.add(order);
        processOrder(order);
        return order;
    }
    
    public void processOrder(Order order) {
        cookService.acceptOrder(order);
    }
    
    public List<Order> getOrders() {
        return orders;
    }
}
