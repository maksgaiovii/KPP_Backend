package software.kosiv.pizzaflow.controller.websocket;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import software.kosiv.pizzaflow.dto.*;
import software.kosiv.pizzaflow.event.*;
import software.kosiv.pizzaflow.mapper.EventDtoMapper;

@Controller
public class WebSocketEventController { // todo: write Event-DTO mappers
    
    private SimpMessagingTemplate template;
    private EventDtoMapper mapper;
    
    public WebSocketEventController(SimpMessagingTemplate template, EventDtoMapper mapper) {
        this.template = template;
        this.mapper = mapper;
    }
    
    @EventListener
    public void handleCustomerCreatedEvent(CustomerCreatedEvent event) {
        String destination = "/topic/customer-created";
        CreatedCustomerDto dto = mapper.map(event);
        template.convertAndSend(destination, dto);
    }
    
    @EventListener
    public void handleNewCustomerInQueueEvent(NewCustomerInQueueEvent event) {
        String destination = "/topic/new-customer-in-queue";
        NewCustomerInQueueDto dto = mapper.map(event);
        
        template.convertAndSend(destination, dto);
    }
    
    
    
    @EventListener
    public void handleOrderAcceptedEvent(OrderAcceptedEvent event) {
        String destination = "/topic/order-accepted";
        OrderAcceptedDto dto = mapper.map(event);
        template.convertAndSend(destination, dto);
    }
    
    @EventListener
    public void handleOrderCompleted(OrderCompletedEvent event) {
        String destination = "/topic/order-completed";
        OrderCompletedDto dto = mapper.map(event);
        template.convertAndSend(destination, dto);
    }
    
    @EventListener
    public void handleDishPreparationStarted(DishPreparationStartedEvent event) {
        String destination = "/topic/dish-preparation-started";
        DishPreparationStartedDto dto = mapper.map(event);
        template.convertAndSend(destination, dto);
    }
    
    @EventListener
    public void handleDishPreparationCompletedEvent(DishPreparationCompletedEvent event) {
        String destination = "/topic/dish-preparation-completed";
        DishPreparationCompletedDto dto = mapper.map(event);
        template.convertAndSend(destination, dto);
    }
    
    
}
