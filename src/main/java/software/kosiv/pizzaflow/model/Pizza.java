package software.kosiv.pizzaflow.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Pizza extends Dish {
    private final PizzaPreparationStrategy preparationStrategy;
    private PizzaState state;
    
    public Pizza(List<PizzaPreparationStep> steps) {
        super();
        this.state = PizzaState.INITIAL;
        this.preparationStrategy = new PizzaPreparationStrategy(steps);
    }
    
    @Override
    public PizzaState toNextState() {
        state = preparationStrategy.toNextState();
        if (state == PizzaState.COMPLETED) {
            isCompleted = true;
        }
        return state;
    }
    
    @Override
    public DishState getNextState() {
        return preparationStrategy.getNextState();
    }
    
    @Override
    public PizzaState getState() {
        return state;
    }
    
    public enum PizzaState implements DishState {
        INITIAL("initial"),
        DOUGH_PREPARATION("dough preparation"),
        DOUGH_ROLLING("dough rolling"),
        SAUCE_ADDITION("sauce addition"),
        TOPPING_ADDITION("topping addition"),
        BAKING("baking"),
        FINISHING_TOUCHES("finishing touches"),
        COMPLETED("completed");
        
        private final String stateName;
        
        PizzaState(String stateName) {
            this.stateName = stateName;
        }
        
        @Override
        public String getStateName() {
            return stateName;
        }
    }
    
    public static class PizzaPreparationStrategy implements PreparationStrategy<PizzaState> {
        private final Queue<PreparationStep<PizzaState>> preparationSteps;
        
        public PizzaPreparationStrategy(List<PizzaPreparationStep> preparationStepList) {
            preparationSteps = new LinkedList<>(preparationStepList);
        }
        
        @Override
        public PizzaState toNextState() {
            var nextStep = preparationSteps.poll();
            return nextStep != null
                           ? nextStep.execute()
                           : PizzaState.COMPLETED;
        }
        
        @Override
        public PizzaState getNextState() {
            var nextStep = preparationSteps.peek();
            return nextStep != null
                           ? nextStep.getNextState()
                           : PizzaState.COMPLETED;
        }
    }
}
