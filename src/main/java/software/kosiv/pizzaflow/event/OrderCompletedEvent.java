package software.kosiv.pizzaflow.event;

import org.springframework.context.ApplicationEvent;
import software.kosiv.pizzaflow.model.Order;

public class OrderCompletedEvent extends ApplicationEvent {
    private final Order order;
    
    public OrderCompletedEvent(Object source, Order order) {
        super(source);
        this.order = order;
    }
    
    public Order getOrder() {
        return order;
    }
}
