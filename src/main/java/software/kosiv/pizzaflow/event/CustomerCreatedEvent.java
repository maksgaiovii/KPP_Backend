package software.kosiv.pizzaflow.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import software.kosiv.pizzaflow.model.Customer;

@Getter
public class CustomerCreatedEvent extends ApplicationEvent {
    
    private final Customer customer;
    
    public CustomerCreatedEvent(Object source, Customer customer) {
        super(source);
        this.customer = customer;
    }
    
    @Override
    public String toString() {
        return "CustomerCreatedEvent{" +
                       "customer=" + customer +
                       '}';
    }
}
