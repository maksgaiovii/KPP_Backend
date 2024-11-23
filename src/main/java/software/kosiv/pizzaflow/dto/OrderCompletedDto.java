package software.kosiv.pizzaflow.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record OrderCompletedDto(UUID orderId,
                                UUID customerId,
                                UUID cashRegisterId,
                                LocalDateTime completedAt) {
}
