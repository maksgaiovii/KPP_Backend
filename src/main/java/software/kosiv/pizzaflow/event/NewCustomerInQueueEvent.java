package software.kosiv.pizzaflow.event;

import org.springframework.context.ApplicationEvent;
import software.kosiv.pizzaflow.model.CashRegister;
import software.kosiv.pizzaflow.model.Customer;

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
