package software.kosiv.pizzaflow.model.dish;

public interface PreparationStep<T extends DishState> {
    T execute();
    T getNextState();
}
