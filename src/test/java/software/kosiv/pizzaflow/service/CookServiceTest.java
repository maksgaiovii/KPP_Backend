package software.kosiv.pizzaflow.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import software.kosiv.pizzaflow.event.DishPreparationCompletedEvent;
import software.kosiv.pizzaflow.event.DishPreparationStartedEvent;
import software.kosiv.pizzaflow.model.*;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class CookServiceTest {



    @InjectMocks
    private CookService cookService;

    @Mock
    private Cook cook;

    @Mock
    private Order order;
    

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // ініціалізація моків
    }

    @Test
    void testAcceptOrder() {
        // Перевіряємо, чи додається замовлення в чергу
        cookService.acceptOrder(order);

        // Перевіряємо, чи замовлення було додано в чергу
        assertFalse(cookService.getOrderQueue().isEmpty());
        assertEquals(1, cookService.getOrderQueue().size());
    }

    @Test
    void testSetCookCount() {
        cookService.setCookCount(5);

        assertEquals(5, cookService.getCooks().size());
    }

    @Test
    void testGetCooks() {
        // Перевіряємо, чи повертається правильний список кухарів
        cookService.setCookCount(3);

        List<Cook> cooks = cookService.getCooks();
        assertNotNull(cooks);
        assertEquals(3, cooks.size());
    }


}
