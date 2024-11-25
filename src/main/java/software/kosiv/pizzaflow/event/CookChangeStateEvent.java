package software.kosiv.pizzaflow.event;

import org.springframework.context.ApplicationEvent;
import software.kosiv.pizzaflow.model.cook.Cook;
import software.kosiv.pizzaflow.model.cook.CookStatus;

public class CookChangeStateEvent extends ApplicationEvent {
    private final Cook cook;
    private final CookStatus previousStatus;

    public CookChangeStateEvent(Object source, Cook cook, CookStatus previousStatus) {
        super(source);
        this.cook = cook;
        this.previousStatus = previousStatus;
    }

    @Override
    public String toString() {
        return "CookChangeStateEvent{" +
                       "cook=" + cook +
                       ", previousStatus=" + previousStatus +
                       '}';
    }
}
