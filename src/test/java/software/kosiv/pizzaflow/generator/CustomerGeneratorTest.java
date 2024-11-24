package software.kosiv.pizzaflow.generator;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import software.kosiv.pizzaflow.generator.CustomerGenerator;
import software.kosiv.pizzaflow.model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CustomerGeneratorTest {
    private CustomerGenerator customerGenerator;
    private Menu menu;

    @BeforeEach
    void setUp() {
        customerGenerator = new CustomerGenerator();

        List<MenuItem> menuItems = Arrays.asList(
                new PizzaMenuItem("Pizza Margherita", new ArrayList<>(), new ArrayList<>()),
                new PizzaMenuItem("Pepperoni Pizza", new ArrayList<>(), new ArrayList<>()),
                new PizzaMenuItem("Hawaiian Pizza", new ArrayList<>(), new ArrayList<>()),
                new PizzaMenuItem("Vegetarian Pizza", new ArrayList<>(), new ArrayList<>()),
                new PizzaMenuItem("BBQ Chicken Pizza", new ArrayList<>(), new ArrayList<>())
        );
        menu = new Menu(menuItems);
    }

    @Test
    void testGenerateCustomer() {
        Customer customer1 = customerGenerator.generateCustomer();
        Customer customer2 = customerGenerator.generateCustomer();

        assertEquals("Customer1", customer1.getName());
        assertEquals("Customer2", customer2.getName());
    }

    @Test
    void testGenerateCustomerWithOrder() {
        Customer customer = customerGenerator.generateCustomerWithOrder(menu);

        assertNotNull(customer);

        Order order = customer.getOrder();
        assertNotNull(order);

        List<OrderItem> orderItems = order.getOrderItems();
        assertNotNull(orderItems);
        assertTrue(orderItems.size() >= 1 && orderItems.size() <= 5);


        for (OrderItem item : orderItems) {
            MenuItem menuItem = item.getMenuItem();
            assertTrue(menu.getMenuItems().contains(menuItem));
        }

        assertEquals(customer, order.getCustomer());
    }

    @Test
    void testGenerateCustomerWithEmptyMenu() {
        Menu emptyMenu = new Menu(Collections.emptyList());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            customerGenerator.generateCustomerWithOrder(emptyMenu);
        });

        assertNotNull(exception, "The method should throw an exception for an empty menu");
    }

    @Test
    void testMultipleCustomersWithOrders() {
        Customer customer1 = customerGenerator.generateCustomerWithOrder(menu);
        Customer customer2 = customerGenerator.generateCustomerWithOrder(menu);

        assertNotEquals(customer1.getName(), customer2.getName());
        assertNotEquals(customer1.getOrder(), customer2.getOrder());

        assertNotNull(customer1.getOrder());
        assertNotNull(customer2.getOrder());
    }
}