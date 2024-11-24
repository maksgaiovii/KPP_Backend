package software.kosiv.pizzaflow.model;

public class WholeDishStrategy implements ICookStrategy {
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
    public ICookStrategy clone() {
        return new WholeDishStrategy();
    }
}
