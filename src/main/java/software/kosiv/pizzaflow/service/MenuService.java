package software.kosiv.pizzaflow.service;

import software.kosiv.pizzaflow.model.menu.Menu;
import software.kosiv.pizzaflow.model.menu.MenuItem;

public interface MenuService {
    void addMenuItem(MenuItem menuItem);
    
    void removeMenuItem(MenuItem menuItem);
    
    Menu getMenu();
}
