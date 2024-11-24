package software.kosiv.pizzaflow.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
import software.kosiv.pizzaflow.event.NewCustomerInQueueEvent;
import software.kosiv.pizzaflow.event.OrderAcceptedEvent;
import software.kosiv.pizzaflow.event.OrderCompletedEvent;
import software.kosiv.pizzaflow.model.CashRegister;
import software.kosiv.pizzaflow.model.Customer;
import software.kosiv.pizzaflow.model.Order;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class CashRegisterServiceTest {

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @Mock
    private OrderService orderService;

    @InjectMocks
    private CashRegisterService cashRegisterService;

    private CashRegister cashRegister;

    @BeforeEach
    public void setUp() {
        cashRegister = new CashRegister();
        cashRegisterService.setCashRegistersCount(1);
    }

    @Test
    public void testAddCustomer() {
        Customer customer = new Customer("John Doe");

        cashRegisterService.addCustomer(customer);

        assertEquals(1, cashRegister.queueSize());

        verify(eventPublisher).publishEvent(any(NewCustomerInQueueEvent.class));

        verify(orderService).processOrder(any(Order.class));
        verify(eventPublisher).publishEvent(any(OrderAcceptedEvent.class));
    }




}
