package software.kosiv.pizzaflow.model;

public interface PreparationStrategy<T extends DishState> {
    T toNextState();
    T getNextState();
}
