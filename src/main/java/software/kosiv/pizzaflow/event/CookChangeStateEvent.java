package software.kosiv.pizzaflow.event;

import org.springframework.context.ApplicationEvent;
import software.kosiv.pizzaflow.message.CookChangeStateMessage;

public class CookChangeStateEvent extends ApplicationEvent {
    public CookChangeStateEvent(CookChangeStateMessage cookChangeStateMessage) {
        super(cookChangeStateMessage);
    }
}
