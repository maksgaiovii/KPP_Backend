package software.kosiv.pizzaflow.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import software.kosiv.pizzaflow.event.OrderCompletedEvent;
import software.kosiv.pizzaflow.model.CashRegister;
import software.kosiv.pizzaflow.model.Customer;
import software.kosiv.pizzaflow.model.Order;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class CashRegisterService {
    private final ApplicationEventPublisher eventPublisher;
    private OrderService orderService;
    private List<CashRegister> cashRegisters = new ArrayList<>();
    
    public CashRegisterService(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }
    
    public void addCustomer(Customer customer) {
        var cashRegister = cashRegisters.stream()
                                        .min(Comparator.comparing(CashRegister::queueSize))
                                        .orElseThrow(IllegalStateException::new); // fixme: change exception class
        cashRegister.addCustomer(customer);
        if (cashRegister.queueSize() == 1) {
            processNextCustomer(cashRegister);
        }
    }
    
    public void closeOrder(Order order) {
        var cashRegister = order.getCashRegister();
        cashRegister.removeCustomer(order.getCustomer());
        eventPublisher.publishEvent(new OrderCompletedEvent(this, order));
        processNextCustomer(cashRegister);
    }
    
    private void processNextCustomer(CashRegister cashRegister) {
        if (cashRegister.queueSize() != 0) {
            Order order = cashRegister.nextCustomer().getOrder();
            order.setCashRegister(cashRegister);
            orderService.processOrder(order);
        }
    }
    
    @Autowired
    public void setOrderService(@Lazy OrderService orderService) { // circular dependency fix
        this.orderService = orderService;
    }
    
    public void setCashRegistersCount(int count) {
        cashRegisters = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            cashRegisters.add(new CashRegister());
        }
    }
}
