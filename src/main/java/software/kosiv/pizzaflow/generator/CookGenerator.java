package software.kosiv.pizzaflow.generator;

import net.datafaker.Faker;
import software.kosiv.pizzaflow.model.cook.Cook;
import software.kosiv.pizzaflow.model.dish.DishState;
import software.kosiv.pizzaflow.model.dish.Pizza;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CookGenerator {

    private static final Faker faker = new Faker(Locale.ITALIAN);

    public static List<Cook> generate(int count) {
        List<Cook> cookList = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            Cook cook = new Cook(faker.name().firstName() + " " + faker.name().lastName());
            addAllPizzaStates(cook);
            cookList.add(cook);
        }
        return cookList;
    }

    private static void addAllPizzaStates(Cook cook) {
        for (Pizza.PizzaState state : Pizza.PizzaState.values()) {
            cook.addSkill(state);
        }
    }

    public static List<Cook> generate(int count, List<DishState> states) {
        List<Cook> cookList = new ArrayList<>(count);

        int statesPerCook = Math.max(1, states.size() / count) * 2;
        int extraStates = states.size() % count;

        int stateIndex = 0;

        for (int i = 0; i < count; i++) {
            Cook cook = new Cook(faker.name().firstName() + " " + faker.name().lastName());

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