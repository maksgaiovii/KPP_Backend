package software.kosiv.pizzaflow.generator;

import software.kosiv.pizzaflow.model.Cook;
import software.kosiv.pizzaflow.model.DishState;
import software.kosiv.pizzaflow.model.Pizza;

import java.util.ArrayList;
import java.util.List;

public class CookGenerator {
    public static List<Cook> generate(int count) {
        List<Cook> cookList = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            Cook cook = new Cook("Name" + i);
            addAllPizzaStates(cook);
            cookList.add(cook);
        }
        return cookList;
    }

    private static void addAllPizzaStates(Cook cook) {
        cook.addSkill(Pizza.PizzaState.DOUGH_PREPARED);
        cook.addSkill(Pizza.PizzaState.DOUGH_ROLLED);
        cook.addSkill(Pizza.PizzaState.SAUCE_ADDED);
        cook.addSkill(Pizza.PizzaState.TOPPING_ADDED);
        cook.addSkill(Pizza.PizzaState.BAKED);
        cook.addSkill(Pizza.PizzaState.FINISHING_TOUCHES);
        cook.addSkill(Pizza.PizzaState.COMPLETED);
    }

    public static List<Cook> generate(int count, List<DishState> states) {
        List<Cook> cookList = new ArrayList<>(count);

        int statesPerCook = Math.max(1, states.size() / count) * 2;
        int extraStates = states.size() % count;

        int stateIndex = 0;

        for (int i = 0; i < count; i++) {
            Cook cook = new Cook("Cook" + (i + 1));

            int skillsForThisCook = statesPerCook + (extraStates > 0 ? 1 : 0);
            if (extraStates > 0) {
                extraStates--;
            }

            for (int j = 0; j < skillsForThisCook; j++) {
                cook.addSkill(states.get(stateIndex));
                stateIndex = (stateIndex + 1) % states.size();
            }

            cookList.add(cook);
        }

        return cookList;
    }
}