package software.kosiv.pizzaflow.event;

public interface EventListener<T> {
    void onEvent(T event);
}
