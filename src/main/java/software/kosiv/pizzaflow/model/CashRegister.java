package software.kosiv.pizzaflow.model;

import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class CashRegister {
    private final UUID uuid = UUID.randomUUID();
    private final BlockingQueue<Customer> customersQueue = new LinkedBlockingQueue<>();
    
    public void addCustomer(Customer customer) {
        customersQueue.add(customer);
    }
    
    public Customer removeCustomer(Customer customer) {
        if (customersQueue.peek().equals(customer)) {
            return customersQueue.poll();
        }
        return null;
    }
    
    public Customer nextCustomer(){
        return customersQueue.peek();
    }
    
    public int queueSize() {
        return customersQueue.size();
    }
    
    @Override
    public String toString() {
        return "CashRegister{" +
                       "uuid=" + uuid +
                       '}';
    }
}
