package software.kosiv.pizzaflow.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import software.kosiv.pizzaflow.model.Order;
import software.kosiv.pizzaflow.model.Customer;
import software.kosiv.pizzaflow.model.CashRegister;
import software.kosiv.pizzaflow.model.Dish;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class OrderServiceTest {

    @Mock
    private CookService cookService;

    @Mock
    private CashRegisterService cashRegisterService;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testProcessOrder_shouldAddOrderToActiveOrders() {
        // Given
        Order order = mock(Order.class);

        // When
        orderService.processOrder(order);

        // Then
        assertTrue(orderService.getActiveOrders().contains(order), "Order should be added to active orders");
        verify(cookService).acceptOrder(order); // Verify that the cook service was called
    }

    @Test
    void testCheckForCompletedOrders_shouldCloseCompletedOrders() {
        // Given
        Order completedOrder = mock(Order.class);
        CashRegister cashRegister = mock(CashRegister.class);
        LocalDateTime now = LocalDateTime.now();
        when(completedOrder.getCompletedAt()).thenReturn(now);
        when(completedOrder.getCashRegister()).thenReturn(cashRegister);

        orderService.processOrder(completedOrder); // Add the order to active orders

        // When
        orderService.checkForCompletedOrders();

        // Then
        verify(cashRegisterService).closeOrder(completedOrder); // Verify that closeOrder was called
        assertFalse(orderService.getActiveOrders().contains(completedOrder), "Order should be removed from active orders after completion");
    }

    @Test
    void testCheckForCompletedOrders_shouldNotCloseIncompleteOrders() {
        // Given
        Order incompleteOrder = mock(Order.class);
        when(incompleteOrder.getCompletedAt()).thenReturn(null); // Order is not completed

        orderService.processOrder(incompleteOrder); // Add the order to active orders

        // When
        orderService.checkForCompletedOrders();

        // Then
        verify(cashRegisterService, never()).closeOrder(incompleteOrder); // Verify that closeOrder was NOT called
        assertTrue(orderService.getActiveOrders().contains(incompleteOrder), "Incomplete order should remain in active orders");
    }

    @Test
    void testProcessOrder_shouldInvokeAcceptOrderFromCookService() {
        // Given
        Order order = mock(Order.class);

        // When
        orderService.processOrder(order);

        // Then
        verify(cookService).acceptOrder(order); // Verify that the cook service was invoked to accept the order
    }

    @Test
    void testScheduledCheckForCompletedOrders_shouldHandleMultipleOrders() throws InterruptedException {
        // Given
        Order order1 = mock(Order.class);
        Order order2 = mock(Order.class);


        LocalDateTime now = LocalDateTime.now();
        when(order1.getCompletedAt()).thenReturn(now);
        when(order2.getCompletedAt()).thenReturn(now);

        orderService.processOrder(order1);
        orderService.processOrder(order2);

        // Simulate a delay for scheduled tasks to run
        TimeUnit.SECONDS.sleep(1);

        // When
        orderService.checkForCompletedOrders();

        // Then
        verify(cashRegisterService, times(2)).closeOrder(any(Order.class)); // Verify closeOrder was called for both orders
        assertTrue(orderService.getActiveOrders().isEmpty(), "All completed orders should be removed");
    }
}
