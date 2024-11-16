package software.kosiv.pizzaflow.service;

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
    private final SimulationService simulationService;
    private final CashRegisterService cashRegisterService;
    private final MenuService menuService;
    private final ApplicationEventPublisher eventPublisher;
    private final CustomerGenerator generator = new CustomerGenerator();
    private final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
    private CustomerGenerationStrategy strategy = CustomerGenerationStrategy.MEDIUM;
    
    public CustomerService(SimulationService simulationService,
                           CashRegisterService cashRegisterService,
                           MenuService menuService,
                           ApplicationEventPublisher eventPublisher) {
        this.simulationService = simulationService;
        this.cashRegisterService = cashRegisterService;
        this.menuService = menuService;
        this.eventPublisher = eventPublisher;
    }
    
    public void createCustomer() {
        if (simulationService.getSimulationStatus() == SimulationStatus.RUNNING) {
            Customer customer = generator.generateCustomerWithOrder(menuService.getMenu());
            publishCustomerCreatedEvent(customer);
            cashRegisterService.addCustomer(customer);
        }
    }
    
    private void publishCustomerCreatedEvent(Customer customer) {
        var event = new CustomerCreatedEvent(this, customer);
        eventPublisher.publishEvent(event);
    }
    
    private void setExecutorService() {
        executorService.scheduleWithFixedDelay(this::createCustomer,
                                               0,
                                               strategy.getGenerationFrequencyInSeconds(),
                                               TimeUnit.SECONDS);
    }
    
    public CustomerGenerationStrategy getStrategy() {
        return strategy;
    }
    
    public void setStrategy(CustomerGenerationStrategy strategy) {
        this.strategy = strategy;
        executorService.shutdown();
        setExecutorService();
    }
}
