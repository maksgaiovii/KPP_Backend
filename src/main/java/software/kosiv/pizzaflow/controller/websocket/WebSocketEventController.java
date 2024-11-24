package software.kosiv.pizzaflow.controller.websocket;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import software.kosiv.pizzaflow.dto.*;
import software.kosiv.pizzaflow.event.*;

import java.time.LocalDateTime;

@Controller
@SendTo("/topic")
public class WebSocketEventController { // todo: write Event-DTO mappers
    
    public WebSocketEventController() {
    }
    
    @EventListener
    @SendTo("/customer-created")
    public CreatedCustomerDto handleCustomerCreatedEvent(CustomerCreatedEvent event) {
        return new CreatedCustomerDto(event.getCustomer().getId(),
                                                        event.getCustomer().getName(),
                                                        LocalDateTime.now());
    }
    
    @EventListener
    @SendTo("/new-customer-in-queue")
    public NewCustomerInQueueDto handleNewCustomerInQueueEvent(NewCustomerInQueueEvent event) {
        return new NewCustomerInQueueDto(event.getCustomer().getId(),
                                                              event.getCashRegister().getId());
        
    }
    
    
    
    @EventListener
    @SendTo("/order-accepted")
    public OrderAcceptedDto handleOrderAcceptedEvent(OrderAcceptedEvent event) {
        return new OrderAcceptedDto(event.getOrder().getId(),
                                                    event.getOrder()
                                                         .getOrderItems()
                                                         .stream()
                                                         .map(i -> i.getMenuItem().getName())
                                                         .toList(),
                                                    event.getOrder().getCustomer().getId(),
                                                    event.getCashRegister().getId(),
                                                    LocalDateTime.now());
    }
    
    @EventListener
    @SendTo("/order-completed")
    public OrderCompletedDto handleOrderCompleted(OrderCompletedEvent event) {
        return new OrderCompletedDto(event.getOrder().getId(),
                                                      event.getOrder().getCustomer().getId(),
                                                      event.getCashRegister().getId(),
                                                      event.getOrder().getCompletedAt());
    }
    
    @EventListener
    @SendTo("/dish-preparation-started")
    public DishPreparationStartedDto handleDishPreparationStarted(DishPreparationStartedEvent event) {
        return new DishPreparationStartedDto(event.getDish().getId(),
                                                                      event.getDish()
                                                                           .getOrderItem()
                                                                           .getMenuItem()
                                                                           .getName(),
                                                                      event.getNextDishState(),
                                                                      event.getCook().getId());
    }
    
    @EventListener
    @SendTo("/dish-preparation-completed")
    public DishPreparationCompletedDto handleDishPreparationCompletedEvent(DishPreparationCompletedEvent event) {
        return new DishPreparationCompletedDto(event.getDish().getId(),
                                                                          event.getDish()
                                                                               .getOrderItem()
                                                                               .getMenuItem()
                                                                               .getName(),
                                                                          event.getNewDishState(),
                                                                          event.getCook().getId());
    }
    
    
}
