package software.kosiv.pizzaflow.generator;

import software.kosiv.pizzaflow.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CustomerGenerator {
    private int count = 1;
    private final Random random = new Random();
    
    public Customer generateCustomer(){ // todo: better name generation for customer
        return new Customer("Customer" + count++);
    }
    
    public Customer generateCustomerWithOrder(Menu menu){
        Customer customer = generateCustomer();
        generateOrder(menu, customer);
        return customer;
    }
    
    private Order generateOrder(Menu menu, Customer customer){
        int orderItemCount = random.nextInt(1, 6);
        List<MenuItem> menuItems = new ArrayList<>(orderItemCount);
        int menuSize = menu.getMenuItems().size();
        for (int i = 0; i < orderItemCount; i++) {
            menuItems.add(menu.getMenuItems().get(random.nextInt(0, menuSize)));
        }
        return new Order(menuItems, customer);
    }
}
