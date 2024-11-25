package software.kosiv.pizzaflow.mapper;

import org.springframework.stereotype.Component;
import software.kosiv.pizzaflow.dto.*;
import software.kosiv.pizzaflow.event.*;

import java.time.LocalDateTime;

@Component
public class EventDtoMapper {
    
    public CreatedCustomerDto map(CustomerCreatedEvent event) {
        return new CreatedCustomerDto(event.getCustomer().getId(),
                                      event.getCustomer().getName(),
                                      LocalDateTime.now());
    }
    
    public NewCustomerInQueueDto map(NewCustomerInQueueEvent event) {
        return new NewCustomerInQueueDto(event.getCustomer().getId(),
                                         event.getCashRegister().getId());
    }
    
    public OrderAcceptedDto map(OrderAcceptedEvent event) {
        return new OrderAcceptedDto(event.getOrder().getId(),
                                    event.getOrder()
                                         .getOrderItems()
                                         .stream()
                                         .map(i -> i.getMenuItem().getName())
                                         .toList(),
                                    event.getOrder().getCustomer().getId(),
                                    event.getCashRegister().getId(),
                                    LocalDateTime.now());
    }
    
    public OrderCompletedDto map(OrderCompletedEvent event) {
        return new OrderCompletedDto(event.getOrder().getId(),
                                     event.getOrder().getCustomer().getId(),
                                     event.getCashRegister().getId(),
                                     event.getOrder().getCompletedAt());
    }
    
    public DishPreparationStartedDto map(DishPreparationStartedEvent event) {
        return new DishPreparationStartedDto(event.getDish().getId(),
                                             event.getDish()
                                                  .getOrderItem()
                                                  .getMenuItem()
                                                  .getName(),
                                             event.getNextDishState(),
                                             event.getCook().getId());
    }
    
    public DishPreparationCompletedDto map(DishPreparationCompletedEvent event) {
        return new DishPreparationCompletedDto(event.getDish().getId(),
                                               event.getDish()
                                                    .getOrderItem()
                                                    .getMenuItem()
                                                    .getName(),
                                               event.getNewDishState(),
                                               event.getCook().getId());
    }
}
