package software.kosiv.pizzaflow.dto;

import software.kosiv.pizzaflow.model.DishState;

import java.util.UUID;

public record DishPreparationStartedDto(UUID dishID,
                                        String dishName,
                                        DishState nextDishState,
                                        UUID cookId) {
}
