package software.kosiv.pizzaflow.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import software.kosiv.pizzaflow.service.CustomerGenerationStrategy;

@Component
@Getter
@Setter
public class SimulationConfig {
    private int cooksNumber;
    private int cashRegistersNumber;
    private boolean specializedCooksMode;
    private CustomerGenerationStrategy customerGenerationStrategy;

    public void update(SimulationConfig config){
        this.cooksNumber = config.cooksNumber;
        this.cashRegistersNumber = config.cashRegistersNumber;
        this.specializedCooksMode = config.specializedCooksMode;
        this.customerGenerationStrategy = config.customerGenerationStrategy;
    }

    public void update(int cooksNumber, int cashRegistersNumber, boolean specializedCooksMode, CustomerGenerationStrategy customerGenerationStrategy) {
        this.cooksNumber = cooksNumber;
        this.cashRegistersNumber = cashRegistersNumber;
        this.specializedCooksMode = specializedCooksMode;
        this.customerGenerationStrategy = customerGenerationStrategy;
    }
}
