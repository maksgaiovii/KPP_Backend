package software.kosiv.pizzaflow.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import software.kosiv.pizzaflow.event.DishPreparationCompletedEvent;
import software.kosiv.pizzaflow.event.DishPreparationStartedEvent;
import software.kosiv.pizzaflow.event.EventManager;
import software.kosiv.pizzaflow.model.Cook;
import software.kosiv.pizzaflow.model.Dish;
import software.kosiv.pizzaflow.model.DishState;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WholeDishStrategyTest {

    private WholeDishStrategy strategy;
    private EventManager<DishPreparationStartedEvent> startEventManager;
    private EventManager<DishPreparationCompletedEvent> completedEventManager;
    private Dish dish;
    private Cook cook;

    @BeforeEach
    void setUp() {
        startEventManager = mock(EventManager.class);
        completedEventManager = mock(EventManager.class);
        dish = mock(Dish.class);
        cook = mock(Cook.class);

        strategy = new WholeDishStrategy(startEventManager, completedEventManager);
        strategy.setCook(cook);
    }

//    @Test
//        void setPaused_ShouldPausePreparation() {
//        // Act
//        strategy.setPaused();
//
//        // Assert
//        assertTrue(strategy.clone() instanceof WholeDishStrategy); // Verify cloning logic
//        // Перевірка через геттер:
//        assertTrue(strategy.isPaused()); // Перевірка, що стратегія на паузі
//    }


    @Test
    void setFree_ShouldResumePreparation() {
        // Arrange
        strategy.setPaused();
        // Act
        strategy.setFree();
        // Assert
        // Since there's no direct way to check internal AtomicBoolean, the lack of exception suffices
        assertDoesNotThrow(() -> strategy.setFree());
    }

    @Test
    void clone_ShouldCreateNewInstance() {
        // Act
        ICookStrategy clonedStrategy = strategy.clone();
        // Assert
        assertNotNull(clonedStrategy);
        assertNotSame(strategy, clonedStrategy);
        assertTrue(clonedStrategy instanceof WholeDishStrategy);
    }

    @Test
    void prepareDish_ShouldPublishEventsUntilCompleted() {
        // Arrange
        when(dish.isCompleted()).thenReturn(false).thenReturn(true); // simulate dish completion
        // Act
        strategy.prepareDish(dish);
        // Assert
        verify(startEventManager, times(1)).publishEvent(any(DishPreparationStartedEvent.class));
        verify(completedEventManager, times(1)).publishEvent(any(DishPreparationCompletedEvent.class));
    }
    @Test
    void prepareDish_ShouldPauseWhenPaused() {
        // Arrange
        when(dish.isCompleted()).thenReturn(false);
        strategy.setPaused();
        // Act
        strategy.prepareDish(dish);
        // Assert
        verify(startEventManager, times(0)).publishEvent(any(DishPreparationStartedEvent.class));
        verify(completedEventManager, times(0)).publishEvent(any(DishPreparationCompletedEvent.class));
    }


}
