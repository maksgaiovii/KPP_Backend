package software.kosiv.pizzaflow.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import software.kosiv.pizzaflow.model.CashRegister;
import software.kosiv.pizzaflow.model.Order;

@Getter
public class OrderCompletedEvent extends ApplicationEvent {
    private final Order order;
    private final CashRegister cashRegister;
    
    public OrderCompletedEvent(Object source, Order order, CashRegister cashRegister) {
        super(source);
        this.order = order;
        this.cashRegister = cashRegister;
    }
    
    @Override
    public String toString() {
        return "OrderCompletedEvent{" +
                       "order=" + order +
                       '}';
    }
}
