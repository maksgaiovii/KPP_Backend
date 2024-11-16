package software.kosiv.pizzaflow.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class SimulationConfig {
    private int cooksNumber;
    private int cashRegistersNumber;
    private int minimumPizzaTime;
    private boolean specializedCooksMode;
    private int clientGenerationInterval;

    public void update(SimulationConfig config){
        this.cooksNumber = config.cooksNumber;
        this.cashRegistersNumber = config.cashRegistersNumber;
        this.minimumPizzaTime = config.minimumPizzaTime;
        this.specializedCooksMode = config.specializedCooksMode;
        this.clientGenerationInterval = config.clientGenerationInterval;
    }

    public void update(int cooksNumber, int cashRegistersNumber, int minimumPizzaTime, boolean specializedCooksMode, int clientGenerationInterval) {
        this.cooksNumber = cooksNumber;
        this.cashRegistersNumber = cashRegistersNumber;
        this.minimumPizzaTime = minimumPizzaTime;
        this.specializedCooksMode = specializedCooksMode;
        this.clientGenerationInterval = clientGenerationInterval;
    }
}
