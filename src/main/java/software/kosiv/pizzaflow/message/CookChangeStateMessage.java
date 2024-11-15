package software.kosiv.pizzaflow.message;

import software.kosiv.pizzaflow.model.CookStatus;

import java.util.UUID;

public record CookChangeStateMessage(UUID cookId,
                                     CookStatus newCookStatus) {
}
