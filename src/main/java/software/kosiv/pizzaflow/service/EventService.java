package software.kosiv.pizzaflow.service;

import org.springframework.stereotype.Service;
import software.kosiv.pizzaflow.event.EventListener;
import software.kosiv.pizzaflow.event.EventManager;

import java.util.HashMap;
import java.util.Map;

@Service
public class EventService {
    private final Map<Class<?>, EventManager<?>> eventManagers = new HashMap<>();

    public <T> void registerEventManager(Class<T> eventType) {
        eventManagers.put(eventType, new EventManager<>());
    }

    @SuppressWarnings("unchecked")
    public <T> EventManager<T> getEventManager(Class<T> eventType) {
        return (EventManager<T>) eventManagers.get(eventType);
    }

    public <T> void registerListener(Class<T> eventType, EventListener<T> listener) {
        EventManager<T> manager = getEventManager(eventType);
        if (manager != null) {
            manager.registerListener(listener);
        } else {
            throw new IllegalArgumentException("No EventManager registered for event type: " + eventType.getName());
        }
    }

    public <T> void publishEvent(T event) {
        Class<?> eventType = event.getClass();
        EventManager<?> manager = eventManagers.get(eventType);
        if (manager != null) {
            @SuppressWarnings("unchecked")
            EventManager<T> typedManager = (EventManager<T>) manager;
            typedManager.publishEvent(event);
        } else {
            throw new IllegalArgumentException("No EventManager registered for event type: " + eventType.getName());
        }
    }
}
