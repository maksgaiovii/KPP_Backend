package software.kosiv.pizzaflow.generator;

import software.kosiv.pizzaflow.model.Cook;

import java.util.ArrayList;
import java.util.List;

public class CookGenerator {
    public static List<Cook> generateCook(int count){ // todo: better name generation for cook
        List<Cook> cookList = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            Cook cook = new Cook("Name" + i);
            cookList.add(cook);
        }
        return cookList;
    }
}
