package software.kosiv.pizzaflow.service;

import software.kosiv.pizzaflow.model.Menu;
import software.kosiv.pizzaflow.model.MenuItem;

import java.util.List;

public class MenuService {
    
    private final Menu menu;
    
    public MenuService(List<MenuItem> menuItems) {
        this.menu = new Menu(menuItems);
    }
    
    public MenuService(Menu menu) {
        this.menu = menu;
    }
    
    public void addMenuItem(MenuItem menuItem) {
        menu.getMenuItems().add(menuItem);
    }
    
    public void removeMenuItem(MenuItem menuItem) {
        menu.getMenuItems().remove(menuItem);
    }
    
    public Menu getMenu() {
        return menu;
    }
}
