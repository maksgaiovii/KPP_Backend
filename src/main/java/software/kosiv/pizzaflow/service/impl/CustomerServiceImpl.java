package software.kosiv.pizzaflow.service.impl;

import lombok.Getter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import software.kosiv.pizzaflow.event.CustomerCreatedEvent;
import software.kosiv.pizzaflow.generator.CustomerGenerator;
import software.kosiv.pizzaflow.model.customer.Customer;
import software.kosiv.pizzaflow.service.ICashRegisterService;
import software.kosiv.pizzaflow.service.ICustomerService;
import software.kosiv.pizzaflow.service.IMenuService;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class CustomerServiceImpl implements ICustomerService {
    private final ICashRegisterService cashRegisterService;
    private final IMenuService menuService;
    private final ApplicationEventPublisher eventPublisher;
    private final CustomerGenerator generator = new CustomerGenerator();
    private ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
    @Getter
    private CustomerGenerationFrequency strategy = CustomerGenerationFrequency.MEDIUM;
    
    public CustomerServiceImpl(ICashRegisterService cashRegisterService,
                               IMenuService menuService,
                           ApplicationEventPublisher eventPublisher) {
        this.cashRegisterService = cashRegisterService;
        this.menuService = menuService;
        this.eventPublisher = eventPublisher;
    }
    
    @Override
    public void createCustomer() {
        Customer customer = generator.generateCustomerWithOrder(menuService.getMenu());
        publishCustomerCreatedEvent(customer);
        cashRegisterService.addCustomer(customer);
    }
    
    @Override
    public void stop() {
        executorService.shutdown();
    }
    
    @Override
    public void resume() {
        setExecutorService();
    }
    
    @Override
    public void terminate() {
        stop();
    }
    
    @Override
    public void setStrategy(CustomerGenerationFrequency strategy) {
        this.strategy = strategy;
        setExecutorService();
    }

    private void publishCustomerCreatedEvent(Customer customer) {
        var event = new CustomerCreatedEvent(this, customer);
        eventPublisher.publishEvent(event);
    }
    
    private void setExecutorService() {
        if (!executorService.isShutdown()) {
            executorService.shutdown();
        }

        executorService = Executors.newScheduledThreadPool(1);
        executorService.scheduleWithFixedDelay(this::createCustomer,
                                               0,
                                               strategy.getGenerationFrequencyInSeconds(),
                                               TimeUnit.SECONDS);
    }
}
