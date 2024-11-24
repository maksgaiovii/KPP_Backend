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
    void testGetMenu() {

        MenuItem menuItem = mock(MenuItem.class);
        List<MenuItem> menuItems = List.of(menuItem);

        when(menuService.getMenu()).thenReturn(new software.kosiv.pizzaflow.model.Menu(menuItems));

        // Перевірка, що метод getMenu повертає правильний список
        List<MenuItem> result = configService.getMenu();
        assertNotNull(result, "Menu should not be null.");
        assertEquals(1, result.size(), "Menu should contain 1 item.");
        assertTrue(result.contains(menuItem), "Menu should contain the mock menu item.");
    }

}
