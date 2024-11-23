package software.kosiv.pizzaflow.websocket;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import software.kosiv.pizzaflow.dto.CreatedCustomerDto;
import software.kosiv.pizzaflow.dto.OrderAcceptedDto;
import software.kosiv.pizzaflow.dto.OrderCompletedDto;
import software.kosiv.pizzaflow.event.CustomerCreatedEvent;
import software.kosiv.pizzaflow.event.OrderAcceptedEvent;
import software.kosiv.pizzaflow.event.OrderCompletedEvent;

import java.time.LocalDateTime;

@Component
@Log4j2
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
        log.info("Send {} to destination {}", dto, destination);
        template.convertAndSend(destination, dto);
    }
    
    @EventListener
    public void handleOrderAcceptedEvent(OrderAcceptedEvent event) {
        String destination = "topic/order-accepted";
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
        String destination = "topic/order-completed";
        OrderCompletedDto dto = new OrderCompletedDto(event.getOrder().getId(),
                                                      event.getOrder().getCustomer().getId(),
                                                      event.getCashRegister().getId(),
                                                      event.getOrder().getCompletedAt());
        template.convertAndSend(destination, dto);
    }
    
    
}
