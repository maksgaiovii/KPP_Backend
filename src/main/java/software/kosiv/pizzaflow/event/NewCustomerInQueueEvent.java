package software.kosiv.pizzaflow.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import software.kosiv.pizzaflow.model.customer.Customer;
import software.kosiv.pizzaflow.model.payment.CashRegister;

@Getter
public class NewCustomerInQueueEvent extends ApplicationEvent {
    private final Customer customer;
    private final CashRegister cashRegister;
    
    
    public NewCustomerInQueueEvent(Object source, Customer customer, CashRegister cashRegister) {
        super(source);
        this.customer = customer;
        this.cashRegister = cashRegister;
    }
    
    @Override
    public String toString() {
        return "NewCustomerInQueueEvent{" +
                       "customer=" + customer +
                       ", cashRegister=" + cashRegister +
                       '}';
    }
}
