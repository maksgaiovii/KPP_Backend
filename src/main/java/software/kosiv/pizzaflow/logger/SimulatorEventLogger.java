package software.kosiv.pizzaflow.logger;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import software.kosiv.pizzaflow.event.*;

@Log4j2
@Component
public class SimulatorEventLogger {
    
    @EventListener
    public void logCustomerCreatedEvent(CustomerCreatedEvent event) {
        log.info("Created: {}", event.getCustomer());
    }
    
    @EventListener
    public void logNewCustomerInQueueEvent(NewCustomerInQueueEvent event) {
        log.info("Customer: {} in queue {}", event.getCustomer(), event.getCashRegister());
    }
    
    @EventListener
    public void logOrderAcceptedEvent(OrderAcceptedEvent event) {
        log.info("Accepted order with id {} made by {} in queue {}",
                 event.getOrder().getId(),
                 event.getOrder().getCustomer(),
                 event.getCashRegister());
    }
    
    @EventListener
    public void logOrderCompleted(OrderCompletedEvent event) {
        log.info("Completed order with id {} made by {} in queue {}",
                 event.getOrder().getId(),
                 event.getOrder().getCustomer(),
                 event.getCashRegister());
    }
    
    @EventListener
    public void logDishPreparationStarted(DishPreparationStartedEvent event) {
        log.info("{} started preparation dish with id {} to state {}",
                 event.getCook(),
                 event.getDish().getId(),
                 event.getNextDishState());
    }
    
    @EventListener
    public void logDishPreparationCompletedEvent(DishPreparationCompletedEvent event) {
        log.info("{} completed preparation dish with id {} to state {}",
                 event.getCook(),
                 event.getDish().getId(),
                 event.getNewDishState());
    }
}
