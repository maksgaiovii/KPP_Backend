package software.kosiv.pizzaflow.controller.websocket;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import software.kosiv.pizzaflow.dto.*;
import software.kosiv.pizzaflow.event.*;

import java.time.LocalDateTime;

@Component
public class WebSocketEventHandler { // todo: write Event-DTO mappers
    
    private SimpMessagingTemplate template;
    
    public WebSocketEventHandler(SimpMessagingTemplate template) {
        this.template = template;
    }
    
    @EventListener
    public void handleCustomerCreatedEvent(CustomerCreatedEvent event) {
        String destination = "/topic/customer-created";
        CreatedCustomerDto dto = new CreatedCustomerDto(event.getCustomer().getId(),
                                                        event.getCustomer().getName(),
                                                        LocalDateTime.now());
        template.convertAndSend(destination, dto);
    }
    
    @EventListener
    public void handleNewCustomerInQueueEvent(NewCustomerInQueueEvent event) {
        String destination = "/topic/new-customer-in-queue";
        NewCustomerInQueueDto dto = new NewCustomerInQueueDto(event.getCustomer().getId(),
                                                              event.getCashRegister().getId());
        
        template.convertAndSend(destination, dto);
    }
    
    
    
    @EventListener
    public void handleOrderAcceptedEvent(OrderAcceptedEvent event) {
        String destination = "/topic/order-accepted";
        OrderAcceptedDto dto = new OrderAcceptedDto(event.getOrder().getId(),
                                                    event.getOrder()
                                                         .getOrderItems()
                                                         .stream()
                                                         .map(i -> i.getMenuItem().getName())
                                                         .toList(),
                                                    event.getOrder().getCustomer().getId(),
                                                    event.getCashRegister().getId(),
                                                    LocalDateTime.now());
        template.convertAndSend(destination, dto);
    }
    
    @EventListener
    public void handleOrderCompleted(OrderCompletedEvent event) {
        String destination = "/topic/order-completed";
        OrderCompletedDto dto = new OrderCompletedDto(event.getOrder().getId(),
                                                      event.getOrder().getCustomer().getId(),
                                                      event.getCashRegister().getId(),
                                                      event.getOrder().getCompletedAt());
        template.convertAndSend(destination, dto);
    }
    
    @EventListener
    public void handleDishPreparationStarted(DishPreparationStartedEvent event) {
        String destination = "/topic/dish-preparation-started";
        DishPreparationStartedDto dto = new DishPreparationStartedDto(event.getDish().getId(),
                                                                      event.getDish()
                                                                           .getOrderItem()
                                                                           .getMenuItem()
                                                                           .getName(),
                                                                      event.getNextDishState(),
                                                                      event.getCook().getId());
        template.convertAndSend(destination, dto);
    }
    
    @EventListener
    public void handleDishPreparationCompletedEvent(DishPreparationCompletedEvent event) {
        String destination = "/topic/dish-preparation-completed";
        DishPreparationCompletedDto dto = new DishPreparationCompletedDto(event.getDish().getId(),
                                                                          event.getDish()
                                                                               .getOrderItem()
                                                                               .getMenuItem()
                                                                               .getName(),
                                                                          event.getNewDishState(),
                                                                          event.getCook().getId());
        template.convertAndSend(destination, dto);
    }
    
    
}
