package software.kosiv.pizzaflow.model;

public interface PreparationStep<T extends DishState> {
    T execute();
    T getNextState();
}
