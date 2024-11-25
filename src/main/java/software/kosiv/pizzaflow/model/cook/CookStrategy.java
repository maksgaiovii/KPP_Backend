package software.kosiv.pizzaflow.model.cook;

import software.kosiv.pizzaflow.model.dish.Dish;
import software.kosiv.pizzaflow.model.dish.DishState;

public interface CookStrategy extends Cloneable {
    DishState prepareDish(Dish dish);

    void setCook(Cook cook);
    
    CookStrategy clone();
}
