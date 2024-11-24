package software.kosiv.pizzaflow.model;

public class OneStepStrategy implements ICookStrategy {
    private Cook cook;

    @Override
    public DishState prepareDish(Dish dish) {
        if (cook.getStatus() == CookStatus.PAUSED) {
            return dish.getState();
        }

        dish.toNextState(cook);
        return dish.getState();
    }

    @Override
    public void setCook(Cook cook) {
        this.cook = cook;
    }

    @Override
    public ICookStrategy clone() {
        return new OneStepStrategy();
    }
}
