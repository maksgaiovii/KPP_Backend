package software.kosiv.pizzaflow.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.context.ApplicationEventPublisher;
import software.kosiv.pizzaflow.event.CustomerCreatedEvent;
import software.kosiv.pizzaflow.generator.CustomerGenerator;
import software.kosiv.pizzaflow.model.Customer;
import software.kosiv.pizzaflow.model.Menu;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class CustomerServiceTest {


    @InjectMocks
    private CustomerService customerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Ініціалізація моків
    }

    @Test
    void setStrategy_shouldUpdateStrategyAndRestartExecutorService() {
        // Arrange
        CustomerGenerationStrategy initialStrategy = customerService.getStrategy();
        customerService.setStrategy(CustomerGenerationStrategy.FAST);
        // Act
        CustomerGenerationStrategy updatedStrategy = customerService.getStrategy();
        // Assert
        assertNotEquals(initialStrategy, updatedStrategy); // Перевіряємо, що стратегія змінилась
    }


    @Test
    void setStrategy_shouldNotRestartExecutorServiceIfNotChanged() {
        // Arrange
        customerService.setStrategy(CustomerGenerationStrategy.FAST);
        ScheduledExecutorService oldExecutorService = mock(ScheduledExecutorService.class); // Мокаємо executorService
        // Act
        customerService.setStrategy(CustomerGenerationStrategy.FAST);
        // Assert
        verify(oldExecutorService, times(0)).shutdown(); // Перевіряємо, що executor не перезапустився
    }

}
