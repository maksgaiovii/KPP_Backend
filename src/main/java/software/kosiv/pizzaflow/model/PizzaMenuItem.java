package software.kosiv.pizzaflow.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.List;

@JsonDeserialize(using = PizzaMenuItemDeserializer.class)
public class PizzaMenuItem extends MenuItem {
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
