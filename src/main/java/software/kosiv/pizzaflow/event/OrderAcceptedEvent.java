package software.kosiv.pizzaflow.event;

import org.springframework.context.ApplicationEvent;
import software.kosiv.pizzaflow.model.CashRegister;
import software.kosiv.pizzaflow.model.Order;

public class OrderAcceptedEvent extends ApplicationEvent {
    private final Order order;
    private final CashRegister cashRegister;
    
    public OrderAcceptedEvent(Object source, Order order, CashRegister cashRegister) {
        super(source);
        this.order = order;
        this.cashRegister = cashRegister;
    }
    
    @Override
    public String toString() {
        return "OrderAcceptedEvent{" +
                       "order=" + order +
                       ", cashRegister=" + cashRegister +
                       '}';
    }
}
