package software.kosiv.pizzaflow.generator;

import net.datafaker.Faker;
import software.kosiv.pizzaflow.model.customer.Customer;
import software.kosiv.pizzaflow.model.menu.Menu;
import software.kosiv.pizzaflow.model.menu.MenuItem;
import software.kosiv.pizzaflow.model.order.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class CustomerGenerator {
    private final Random random = new Random();
    private final Faker faker = new Faker(Locale.ITALIAN);

    public Customer generateCustomer() {
        String customerName = faker.name().firstName() + " " + faker.name().lastName();
        return new Customer(customerName);
    }

    public Customer generateCustomerWithOrder(Menu menu) {
        Customer customer = generateCustomer();
        generateOrder(menu, customer);
        return customer;
    }

    private void generateOrder(Menu menu, Customer customer) {
        int orderItemCount = random.nextInt(1, 6);
        List<MenuItem> menuItems = new ArrayList<>(orderItemCount);
        int menuSize = menu.getMenuItems().size();

        for (int i = 0; i < orderItemCount; i++) {
            menuItems.add(menu.getMenuItems().get(random.nextInt(0, menuSize)));
        }

        new Order(menuItems, customer);
    }
}
