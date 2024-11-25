package software.kosiv.pizzaflow.service;

import software.kosiv.pizzaflow.model.customer.Customer;
import software.kosiv.pizzaflow.model.order.Order;

public interface ICashRegisterService {
    void addCustomer(Customer customer);
    
    void closeOrder(Order order);
    
    void setCashRegistersCount(int count);
}
