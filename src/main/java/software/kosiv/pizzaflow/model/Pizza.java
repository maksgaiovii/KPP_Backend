package software.kosiv.pizzaflow.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Pizza extends Dish {
    private final PizzaPreparationStrategy preparationStrategy;
    private PizzaState state;
    
    public Pizza(OrderItem  orderItem,List<PizzaPreparationStep> steps) {
        super(orderItem);
        this.state = PizzaState.INITIAL;
        this.preparationStrategy = new PizzaPreparationStrategy(steps);
    }
    
    @Override
    public PizzaState toNextState() {
        state = preparationStrategy.toNextState();
        if (state == PizzaState.COMPLETED) {
            complete();
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
    
    private void complete(){
        getOrderItem().complete();
        isCompleted = true;
    }
    
    @Override
    public String toString() {
        return "Pizza{" +
                       "state=" + state +
                       "} " + super.toString();
    }
    
    public enum PizzaState implements DishState {
        INITIAL("initial"),
        DOUGH_PREPARED("dough preparation"),
        DOUGH_ROLLED("dough rolling"),
        SAUCE_ADDED("sauce addition"),
        TOPPING_ADDED("topping addition"),
        BAKED("baking"),
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

        public static List<PizzaState> getPreparationSteps() {
            return List.of(DOUGH_PREPARED, DOUGH_ROLLED, SAUCE_ADDED, TOPPING_ADDED, BAKED, FINISHING_TOUCHES, COMPLETED);
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
