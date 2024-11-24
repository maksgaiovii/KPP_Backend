package software.kosiv.pizzaflow.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import software.kosiv.pizzaflow.service.CustomerGenerationStrategy;
import software.kosiv.pizzaflow.model.ICookStrategy;

@Component
@Getter
@Setter
public class SimulationConfig {
    private int cooksNumber;
    private int cashRegistersNumber;
    private ICookStrategy cookStrategy;
    private CustomerGenerationStrategy customerGenerationStrategy;

    public void update(SimulationConfig config){
        this.cooksNumber = config.cooksNumber;
        this.cashRegistersNumber = config.cashRegistersNumber;
        this.cookStrategy = config.cookStrategy;
        this.customerGenerationStrategy = config.customerGenerationStrategy;
    }

    public void update(int cooksNumber, int cashRegistersNumber, ICookStrategy cookStrategy, CustomerGenerationStrategy customerGenerationStrategy) {
        this.cooksNumber = cooksNumber;
        this.cashRegistersNumber = cashRegistersNumber;
        this.cookStrategy = cookStrategy;
        this.customerGenerationStrategy = customerGenerationStrategy;
    }
}
