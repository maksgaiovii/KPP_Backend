package software.kosiv.pizzaflow.model.dish;

public interface PreparationStrategy<T extends DishState> {
    T toNextState();
    T getNextState();
}
