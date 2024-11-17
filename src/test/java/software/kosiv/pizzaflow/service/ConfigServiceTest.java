package software.kosiv.pizzaflow.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import software.kosiv.pizzaflow.config.SimulationConfig;
import software.kosiv.pizzaflow.dto.StartConfigDto;
import software.kosiv.pizzaflow.model.MenuItem;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ConfigServiceTest {

    @Mock
    private SimulationConfig pizzeriaConfig;

    @Mock
    private MenuService menuService;

    @InjectMocks
    private ConfigService configService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testInitDefaultConfig() {
        // Викликаємо метод ініціалізації конфігурації
        configService.initDefaultConfig();

        // Перевіряємо, що метод update на pizzeriaConfig був викликаний з правильними параметрами
        verify(pizzeriaConfig).update(3, 2, false, 8);
    }

    @Test
    void testGetMenu() {
        // Підготовка тестових даних
        MenuItem menuItem = mock(MenuItem.class);
        List<MenuItem> menuItems = List.of(menuItem);

        // Налаштовуємо menuService так, щоб він повертав наш список
        when(menuService.getMenu()).thenReturn(new software.kosiv.pizzaflow.model.Menu(menuItems));

        // Перевірка, що метод getMenu повертає правильний список
        List<MenuItem> result = configService.getMenu();
        assertNotNull(result, "Menu should not be null.");
        assertEquals(1, result.size(), "Menu should contain 1 item.");
        assertTrue(result.contains(menuItem), "Menu should contain the mock menu item.");
    }

    @Test
    void testGetSimulationConfig() {
        // Налаштовуємо значення для pizzeriaConfig
        when(pizzeriaConfig.getCooksNumber()).thenReturn(3);
        when(pizzeriaConfig.getCashRegistersNumber()).thenReturn(2);
        when(pizzeriaConfig.isSpecializedCooksMode()).thenReturn(false);
        when(pizzeriaConfig.getClientGenerationInterval()).thenReturn(8);

        // Перевірка, що метод getSimulationConfig повертає правильний об'єкт конфігурації
        SimulationConfig result = configService.getSimulationConfig();
        assertNotNull(result, "SimulationConfig should not be null.");
        assertEquals(3, result.getCooksNumber(), "Cooks number should be 3.");
        assertEquals(2, result.getCashRegistersNumber(), "Cash registers number should be 2.");
        assertFalse(result.isSpecializedCooksMode(), "Specialized cooks mode should be false.");
        assertEquals(8, result.getClientGenerationInterval(), "Client generation interval should be 8.");
    }

    @Test
    void testSetSimulationConfig() {
        // Створення нового об'єкта конфігурації
        SimulationConfig newConfig = new SimulationConfig();
        newConfig.update(5, 3, true, 10);

        // Виклик методу setSimulationConfig
        configService.setSimulationConfig(newConfig);

        // Перевірка, що метод update на pizzeriaConfig був викликаний з правильними параметрами
        verify(pizzeriaConfig).update(newConfig);
    }

    @Test
    void testMapToSimulationConfig() {
        // Створення StartConfigDto для тесту
        StartConfigDto inputDto = new StartConfigDto(5, 3, true);

        // Виклик методу mapToSimulationConfig
        SimulationConfig result = configService.mapToSimulationConfig(inputDto);

        // Перевірка, що конфігурація була правильно картована
        assertNotNull(result, "Mapped SimulationConfig should not be null.");
        assertEquals(5, result.getCooksNumber(), "Cooks number should be 5.");
        assertEquals(3, result.getCashRegistersNumber(), "Cash registers number should be 3.");
        assertTrue(result.isSpecializedCooksMode(), "Specialized cooks mode should be true.");
        assertEquals(8, result.getClientGenerationInterval(), "Client generation interval should be 8.");
    }
}
