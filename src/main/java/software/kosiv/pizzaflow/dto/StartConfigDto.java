package software.kosiv.pizzaflow.dto;

public record StartConfigDto(int cooksNumber, int cashRegistersNumber,
                             int minimumPizzaTime, boolean specializedCooksMode) {
}
