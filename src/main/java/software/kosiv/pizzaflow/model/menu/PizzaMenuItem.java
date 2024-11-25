package software.kosiv.pizzaflow.model.menu;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import software.kosiv.pizzaflow.model.dish.Pizza;
import software.kosiv.pizzaflow.model.dish.PizzaPreparationStep;
import software.kosiv.pizzaflow.model.order.OrderItem;

import java.util.List;

@JsonDeserialize(using = PizzaMenuItemDeserializer.class)
public class PizzaMenuItem extends MenuItem {
    private final List<PizzaPreparationStep> steps;
    
    public PizzaMenuItem(String name, List<String> ingredients, List<PizzaPreparationStep> steps) {
        super(name, ingredients);
        this.steps = steps;
    }
    
    @Override
    public Pizza createDish(OrderItem orderItem) {
        return new Pizza(orderItem ,steps);
    }
}
