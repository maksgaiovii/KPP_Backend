package software.kosiv.pizzaflow.model.menu;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import software.kosiv.pizzaflow.model.dish.Pizza;
import software.kosiv.pizzaflow.model.dish.PizzaPreparationStep;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PizzaMenuItemDeserializer extends JsonDeserializer<PizzaMenuItem> {
    @Override
    public PizzaMenuItem deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode node = p.getCodec().readTree(p);
        String name = node.get("name").asText();
        
        List<String> ingredients = new ArrayList<>();
        node.get("ingredients").forEach(ingredientNode -> ingredients.add(ingredientNode.asText()));
        
        List<PizzaPreparationStep> steps = new ArrayList<>();
        node.get("steps").forEach(stepNode -> {
            Pizza.PizzaState nextState = Pizza.PizzaState.valueOf(stepNode.get("nextState").asText());
            long executionTimeInSeconds = stepNode.get("executionTimeInSeconds").asLong();
            steps.add(new PizzaPreparationStep(nextState, executionTimeInSeconds));
        });
        
        return new PizzaMenuItem(name, ingredients, steps);
    }
}
