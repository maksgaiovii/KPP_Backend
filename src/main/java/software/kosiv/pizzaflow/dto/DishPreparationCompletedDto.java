package software.kosiv.pizzaflow.dto;

import software.kosiv.pizzaflow.model.DishState;

import java.util.UUID;

public record DishPreparationCompletedDto(UUID dishID,
                                          String dishName,
                                          DishState newDishState,
                                          UUID cookId) {
}
