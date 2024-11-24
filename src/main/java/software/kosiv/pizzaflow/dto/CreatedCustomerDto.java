package software.kosiv.pizzaflow.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record CreatedCustomerDto(UUID customerId,
                                 String customerName,
                                 LocalDateTime createdAt) {
}
