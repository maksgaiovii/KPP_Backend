package software.kosiv.pizzaflow.model;

import java.util.List;

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
    
    public abstract Dish createDish();
}
