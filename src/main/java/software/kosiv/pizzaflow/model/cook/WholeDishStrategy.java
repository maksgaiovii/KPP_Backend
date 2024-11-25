package software.kosiv.pizzaflow.model.cook;

import software.kosiv.pizzaflow.model.dish.Dish;
import software.kosiv.pizzaflow.model.dish.DishState;

public class WholeDishStrategy implements CookStrategy {
    private Cook cook;

    @Override
    public DishState prepareDish(Dish dish) {
        while (!dish.isCompleted() && cook.getStatus() != CookStatus.PAUSED) {
            dish.toNextState(cook);
        }

        return dish.getState();
    }

    @Override
    public void setCook(Cook cook) {
        this.cook = cook;
    }

    @Override
    public CookStrategy clone() {
        return new WholeDishStrategy();
    }
}
