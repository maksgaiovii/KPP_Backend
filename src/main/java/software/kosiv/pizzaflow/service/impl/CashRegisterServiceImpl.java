package software.kosiv.pizzaflow.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import software.kosiv.pizzaflow.event.NewCustomerInQueueEvent;
import software.kosiv.pizzaflow.event.OrderAcceptedEvent;
import software.kosiv.pizzaflow.event.OrderCompletedEvent;
import software.kosiv.pizzaflow.model.customer.Customer;
import software.kosiv.pizzaflow.model.order.Order;
import software.kosiv.pizzaflow.model.payment.CashRegister;
import software.kosiv.pizzaflow.service.CashRegisterService;
import software.kosiv.pizzaflow.service.OrderService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class CashRegisterServiceImpl implements CashRegisterService {
    private final ApplicationEventPublisher eventPublisher;
    private OrderService orderService;
    private List<CashRegister> cashRegisters = new ArrayList<>();
    
    public CashRegisterServiceImpl(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }
    
    @Override
    public void addCustomer(Customer customer) {
        var cashRegister = cashRegisters.stream()
                                        .min(Comparator.comparing(CashRegister::queueSize))
                                        .orElseThrow(IllegalStateException::new);
        cashRegister.addCustomer(customer);
        publishNewCustomerInQueueEvent(customer, cashRegister);
        if (cashRegister.queueSize() == 1) {
            processNextCustomer(cashRegister);
        }
    }
    
    private void publishNewCustomerInQueueEvent(Customer customer, CashRegister cashRegister) {
        var event = new NewCustomerInQueueEvent(this, customer, cashRegister);
        eventPublisher.publishEvent(event);
    }
    
    @Override
    public void closeOrder(Order order) {
        var cashRegister = order.getCashRegister();
        cashRegister.removeCustomer(order.getCustomer());
        eventPublisher.publishEvent(new OrderCompletedEvent(this, order, cashRegister));
        processNextCustomer(cashRegister);
    }
    
    private void processNextCustomer(CashRegister cashRegister) {
        if (cashRegister.queueSize() != 0) {
            Order order = cashRegister.nextCustomer().getOrder();
            order.setCashRegister(cashRegister);
            orderService.processOrder(order);
            publishOrderAcceptedEvent(order, cashRegister);
        }
    }
    
    private void publishOrderAcceptedEvent(Order order, CashRegister cashRegister) {
        var event = new OrderAcceptedEvent(this, order, cashRegister);
        eventPublisher.publishEvent(event);
    }
    
    @Autowired
    public void setOrderService(@Lazy OrderService orderService) { // circular dependency fix
        this.orderService = orderService;
    }
    
    @Override
    public void setCashRegistersCount(int count) {
        cashRegisters = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            cashRegisters.add(new CashRegister());
        }
    }
}
