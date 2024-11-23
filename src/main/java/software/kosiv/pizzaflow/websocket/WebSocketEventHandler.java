package software.kosiv.pizzaflow.websocket;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import software.kosiv.pizzaflow.dto.CreatedCustomerDto;
import software.kosiv.pizzaflow.event.CustomerCreatedEvent;

import java.time.LocalDateTime;

@Component
@Log4j2
public class WebSocketEventHandler {
    
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
    
}
