package software.kosiv.pizzaflow.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import software.kosiv.pizzaflow.model.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MenuServiceTest {

    private MenuService menuService;

    @BeforeEach
    void setUp() {
        // Створення змінного списку для тестового меню
        PizzaMenuItem pizzaMenuItem = new PizzaMenuItem("Test Pizza",
                List.of("Cheese", "Tomato"),
                List.of(new PizzaPreparationStep(Pizza.PizzaState.BAKED, 10)));

        List<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(pizzaMenuItem);

        menuService = new MenuService(new Menu(menuItems));
    }

    @Test
    void addMenuItem() {
        // Створення нового PizzaMenuItem для тесту
        PizzaMenuItem newPizza = new PizzaMenuItem("New Pizza",
                List.of("Mozzarella", "Basil"),
                List.of(new PizzaPreparationStep(Pizza.PizzaState.BAKED, 10)));
        menuService.addMenuItem(newPizza);

        // Перевірка, що новий елемент додано до меню
        assertTrue(menuService.getMenu().getMenuItems().contains(newPizza),
                "New menu item should be added.");

        // Перевірка, що кількість елементів у меню збільшилась на 1
        assertEquals(2, menuService.getMenu().getMenuItems().size(),
                "Menu size should be increased by 1 after adding a new item.");
    }

    @Test
    void removeMenuItem() {
        // Отримання першого елемента в меню
        MenuItem existingItem = menuService.getMenu().getMenuItems().get(0);
        menuService.removeMenuItem(existingItem);

        // Перевірка, що елемент видалено
        assertFalse(menuService.getMenu().getMenuItems().contains(existingItem),
                "Menu item should be removed.");

        // Перевірка, що кількість елементів у меню зменшилась на 1
        assertEquals(0, menuService.getMenu().getMenuItems().size(),
                "Menu size should be decreased by 1 after removing an item.");
    }

    @Test
    void getMenu() {
        Menu menu = menuService.getMenu();
        assertNotNull(menu, "Menu should not be null.");
        assertFalse(menu.getMenuItems().isEmpty(), "Menu should contain items.");

        // Перевірка, що кількість елементів в меню дорівнює 1 після ініціалізації
        assertEquals(1, menu.getMenuItems().size(),
                "Menu should contain exactly 1 item after initialization.");
    }

    @Test
    void addMultipleItems() {
        // Додавання кількох елементів до меню
        PizzaMenuItem newPizza1 = new PizzaMenuItem("New Pizza 1",
                List.of("Mozzarella", "Basil"),
                List.of(new PizzaPreparationStep(Pizza.PizzaState.BAKED, 10)));
        PizzaMenuItem newPizza2 = new PizzaMenuItem("New Pizza 2",
                List.of("Cheese", "Tomato"),
                List.of(new PizzaPreparationStep(Pizza.PizzaState.BAKED, 15)));

        menuService.addMenuItem(newPizza1);
        menuService.addMenuItem(newPizza2);

        // Перевірка, що всі елементи додано до меню
        assertTrue(menuService.getMenu().getMenuItems().contains(newPizza1),
                "New Pizza 1 should be added.");
        assertTrue(menuService.getMenu().getMenuItems().contains(newPizza2),
                "New Pizza 2 should be added.");

        // Перевірка, що кількість елементів у меню збільшилась на 2
        assertEquals(3, menuService.getMenu().getMenuItems().size(),
                "Menu size should be increased by 2 after adding multiple items.");
    }

    @Test
    void removeNonExistentItem() {
        // Спроба видалити неіснуючий елемент
        MenuItem nonExistentItem = new PizzaMenuItem("Non Existent Pizza",
                List.of("Unknown"),
                List.of(new PizzaPreparationStep(Pizza.PizzaState.BAKED, 10)));

        // Видалення неіснуючого елемента
        menuService.removeMenuItem(nonExistentItem);

        // Перевірка, що розмір меню не змінився
        assertEquals(1, menuService.getMenu().getMenuItems().size(),
                "Menu size should remain the same if non-existent item is removed.");
    }

    @Test
    void addNullMenuItem() {
        menuService.addMenuItem(null);

        assertEquals(2, menuService.getMenu().getMenuItems().size(),
                "Menu size should remain the same if null item is added.");
    }
}
