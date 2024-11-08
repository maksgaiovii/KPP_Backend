package software.kosiv.pizzaflow.model;

import java.util.ArrayList;
import java.util.List;

public class Menu {
    private List<MenuItem> menuItems = new ArrayList<>();
    
    public Menu() {
    }
    
    public Menu(List<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }
    
    public List<MenuItem> getMenuItems() {
        return menuItems;
    }
    
    public void setMenuItems(List<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }
}
