package software.kosiv.pizzaflow.model;

public interface ICookStrategy extends Cloneable {
    DishState prepareDish(Dish dish);

    void setCook(Cook cook);

    ICookStrategy clone();
}
