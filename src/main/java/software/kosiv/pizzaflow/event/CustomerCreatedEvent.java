package software.kosiv.pizzaflow.event;

import org.springframework.context.ApplicationEvent;
import software.kosiv.pizzaflow.model.Customer;

public class CustomerCreatedEvent extends ApplicationEvent {
    
    private final Customer customer;
    
    public CustomerCreatedEvent(Object source, Customer customer) {
        super(source);
        this.customer = customer;
    }
}
