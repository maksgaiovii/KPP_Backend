package software.kosiv.pizzaflow.model;

import java.util.List;

public class PizzaMenuItem extends MenuItem<Pizza> {
    private final List<PizzaPreparationStep> steps;
    
    public PizzaMenuItem(String name, List<String> ingredients, List<PizzaPreparationStep> steps) {
        super(name, ingredients);
        this.steps = steps;
    }
    
    @Override
    public Pizza createDish() {
        return new Pizza(steps);
    }
}
