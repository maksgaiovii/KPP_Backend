package software.kosiv.pizzaflow.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import software.kosiv.pizzaflow.model.cook.CookStrategy;
import software.kosiv.pizzaflow.service.impl.CustomerGenerationFrequency;

@Component
@Getter
@Setter
public class SimulationConfig {
    private int cooksNumber;
    private int cashRegistersNumber;
    private CookStrategy cookStrategy;
    private CustomerGenerationFrequency customerGenerationFrequency;

    public void update(SimulationConfig config){
        this.cooksNumber = config.cooksNumber;
        this.cashRegistersNumber = config.cashRegistersNumber;
        this.cookStrategy = config.cookStrategy;
        this.customerGenerationFrequency = config.customerGenerationFrequency;
    }
    
    public void update(int cooksNumber,
                       int cashRegistersNumber,
                       CookStrategy cookStrategy,
                       CustomerGenerationFrequency customerGenerationFrequency) {
        this.cooksNumber = cooksNumber;
        this.cashRegistersNumber = cashRegistersNumber;
        this.cookStrategy = cookStrategy;
        this.customerGenerationFrequency = customerGenerationFrequency;
    }
}
