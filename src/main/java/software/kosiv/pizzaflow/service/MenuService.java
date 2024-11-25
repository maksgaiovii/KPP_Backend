package software.kosiv.pizzaflow.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import software.kosiv.pizzaflow.model.menu.Menu;
import software.kosiv.pizzaflow.model.menu.MenuItem;
import software.kosiv.pizzaflow.model.menu.PizzaMenuItem;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class MenuService {
    private final Menu menu;
    private ObjectMapper objectMapper = new ObjectMapper();
    InputStream inputStream = getClass().getClassLoader().getResourceAsStream("menu.json");
    
    public MenuService() {
        List<PizzaMenuItem> items = null; // fixme: PizzaMenuItem -> MenuItem
        try {
            items = objectMapper.readValue(inputStream, new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        List<MenuItem> menuItems = new ArrayList<>(items);
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
