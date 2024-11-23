package software.kosiv.pizzaflow.service;

import software.kosiv.pizzaflow.model.Cook;
import software.kosiv.pizzaflow.model.Dish;
import software.kosiv.pizzaflow.model.DishState;

public interface ICookStrategy {
    DishState prepareDish(Dish dish);

    void setPaused();

    void setFree();

    void setCook(Cook cook);
}
