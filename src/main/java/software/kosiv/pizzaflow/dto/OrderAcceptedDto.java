package software.kosiv.pizzaflow.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record OrderAcceptedDto(UUID orderId,
                               List<String> orderItems,
                               UUID customerId,
                               UUID cashRegisterId,
                               LocalDateTime acceptedAt) {
}
