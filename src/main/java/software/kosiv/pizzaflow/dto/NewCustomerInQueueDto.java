package software.kosiv.pizzaflow.dto;

import java.util.UUID;

public record NewCustomerInQueueDto(UUID customerId,
                                    UUID cashRegisterId) {
}
