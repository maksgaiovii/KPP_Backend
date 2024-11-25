package software.kosiv.pizzaflow.model.menu;

import software.kosiv.pizzaflow.model.dish.Dish;
import software.kosiv.pizzaflow.model.order.OrderItem;

import java.util.List;
import java.util.Objects;

public abstract class MenuItem {
    private final String name;
    private final List<String> ingredients;
    
    public MenuItem(String name, List<String> ingredients) {
        this.name = name;
        this.ingredients = ingredients;
    }
    
    public String getName() {
        return name;
    }
    
    public List<String> getIngredients() {
        return ingredients;
    }
    
    public abstract Dish createDish(OrderItem orderItem);
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MenuItem menuItem)) {
            return false;
        }
        return Objects.equals(name, menuItem.name) && Objects.equals(ingredients, menuItem.ingredients);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(name, ingredients);
    }
}
