package software.kosiv.pizzaflow.generator;

import org.junit.jupiter.api.Test;
import software.kosiv.pizzaflow.generator.CookGenerator;
import software.kosiv.pizzaflow.model.Cook;
import software.kosiv.pizzaflow.model.DishState;
import software.kosiv.pizzaflow.model.Pizza;
import software.kosiv.pizzaflow.model.Pizza.PizzaState;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CookGeneratorTest {

    @Test
    public void testGenerateWithDefaultStates() {
        int count = 3;
        List<Cook> cooks = CookGenerator.generate(count);

        assertNotNull(cooks);
        assertEquals(count, cooks.size());

        for (Cook cook : cooks) {
            assertTrue(cook.getSkills().contains(PizzaState.DOUGH_PREPARED));
            assertTrue(cook.getSkills().contains(PizzaState.DOUGH_ROLLED));
            assertTrue(cook.getSkills().contains(PizzaState.SAUCE_ADDED));
            assertTrue(cook.getSkills().contains(PizzaState.TOPPING_ADDED));
            assertTrue(cook.getSkills().contains(PizzaState.BAKED));
            assertTrue(cook.getSkills().contains(PizzaState.FINISHING_TOUCHES));
            assertTrue(cook.getSkills().contains(PizzaState.COMPLETED));
        }
    }

    @Test
    public void testGenerateWithCustomStates() {
        List<DishState> customStates = List.of(PizzaState.DOUGH_PREPARED, PizzaState.DOUGH_ROLLED, PizzaState.SAUCE_ADDED);
        int count = 5;

        List<Cook> cooks = CookGenerator.generate(count, customStates);

        assertNotNull(cooks);
        assertEquals(count, cooks.size());

        assertEquals(5, cooks.size());

        assertTrue(cooks.get(0).getSkills().size() >= 2 && cooks.get(0).getSkills().size() <= 3);
        assertTrue(cooks.get(1).getSkills().size() >= 2 && cooks.get(1).getSkills().size() <= 3);
        assertTrue(cooks.get(2).getSkills().size() >= 2 && cooks.get(2).getSkills().size() <= 3);
    }

    @Test
    public void testGenerateWithMoreStatesThanCooks() {
        List<DishState> customStates = List.of(PizzaState.DOUGH_PREPARED, PizzaState.DOUGH_ROLLED, PizzaState.SAUCE_ADDED, PizzaState.TOPPING_ADDED);
        int count = 2;

        List<Cook> cooks = CookGenerator.generate(count, customStates);

        assertNotNull(cooks);
        assertEquals(count, cooks.size());

        for (Cook cook : cooks) {
            System.out.println(cook.getSkills());
        }

        assertEquals(4, cooks.get(0).getSkills().size());
        assertEquals(4, cooks.get(1).getSkills().size());
    }

}