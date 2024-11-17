package software.kosiv.pizzaflow.service;

import lombok.Getter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import software.kosiv.pizzaflow.event.CustomerCreatedEvent;
import software.kosiv.pizzaflow.generator.CustomerGenerator;
import software.kosiv.pizzaflow.model.Customer;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class CustomerService {
    private final CashRegisterService cashRegisterService;
    private final MenuService menuService;
    private final ApplicationEventPublisher eventPublisher;
    private final CustomerGenerator generator = new CustomerGenerator();
    private ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
    @Getter
    private CustomerGenerationStrategy strategy = CustomerGenerationStrategy.MEDIUM;
    
    public CustomerService(CashRegisterService cashRegisterService,
                           MenuService menuService,
                           ApplicationEventPublisher eventPublisher) {
        this.cashRegisterService = cashRegisterService;
        this.menuService = menuService;
        this.eventPublisher = eventPublisher;
    }
    
    public void createCustomer() {
        Customer customer = generator.generateCustomerWithOrder(menuService.getMenu());
        publishCustomerCreatedEvent(customer);
        cashRegisterService.addCustomer(customer);
    }

    public void stop() {
        executorService.shutdown();
    }

    public void resume() {
        setExecutorService();
    }

    public void terminate() {
        executorService.shutdownNow();
    }

    public void setStrategy(CustomerGenerationStrategy strategy) {
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
