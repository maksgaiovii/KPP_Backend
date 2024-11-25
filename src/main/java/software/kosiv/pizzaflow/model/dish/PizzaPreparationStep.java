package software.kosiv.pizzaflow.model.dish;

import software.kosiv.pizzaflow.model.dish.Pizza.PizzaState;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

public record PizzaPreparationStep(PizzaState nextState,
                                   long executionTimeInSeconds) implements PreparationStep<PizzaState> {
    
    @Override
    public PizzaState execute() {
        try {
            Thread.sleep(Duration.of(executionTimeInSeconds, ChronoUnit.SECONDS));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return nextState;
    }
    
    @Override
    public PizzaState getNextState() {
        return nextState;
    }
}
