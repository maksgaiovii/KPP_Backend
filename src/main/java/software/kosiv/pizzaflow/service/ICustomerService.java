package software.kosiv.pizzaflow.service;

import software.kosiv.pizzaflow.service.impl.CustomerGenerationFrequency;

public interface ICustomerService {
    void createCustomer();
    
    void stop();
    
    void resume();
    
    void terminate();
    
    CustomerGenerationFrequency getStrategy();
    
    void setStrategy(CustomerGenerationFrequency strategy);
}
