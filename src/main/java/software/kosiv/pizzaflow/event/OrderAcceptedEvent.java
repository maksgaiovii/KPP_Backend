package software.kosiv.pizzaflow.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import software.kosiv.pizzaflow.model.order.Order;
import software.kosiv.pizzaflow.model.payment.CashRegister;

@Getter
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
