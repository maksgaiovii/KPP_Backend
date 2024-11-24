package software.kosiv.pizzaflow.event;

import java.util.ArrayList;
import java.util.List;

public class EventManager<T> {
    private final List<EventListener<T>> listeners = new ArrayList<>();

    public void registerListener(EventListener<T> listener) {
        listeners.add(listener);
    }

    public void publishEvent(T event) {
        for (EventListener<T> listener : listeners) {
            listener.onEvent(event);
        }
    }
}
