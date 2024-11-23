package software.kosiv.pizzaflow.service;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import software.kosiv.pizzaflow.config.SimulationConfig;
import software.kosiv.pizzaflow.dto.StartConfigDto;
import software.kosiv.pizzaflow.event.DishPreparationCompletedEvent;
import software.kosiv.pizzaflow.event.DishPreparationStartedEvent;
import software.kosiv.pizzaflow.model.MenuItem;

import java.util.List;

@Service
public class ConfigService implements IConfigService {
    private final SimulationConfig pizzeriaConfig;
    private final MenuService menuService;
    private final EventService eventRegistry;

    public ConfigService(SimulationConfig pizzeriaConfig, MenuService menuService, EventService eventRegistry) {
        this.pizzeriaConfig = pizzeriaConfig;
        this.menuService = menuService;
        this.eventRegistry = eventRegistry;
    }

    @PostConstruct
    public void initDefaultConfig() {
        var startEventManager = eventRegistry.getEventManager(DishPreparationStartedEvent.class);
        var completedEventManager = eventRegistry.getEventManager(DishPreparationCompletedEvent.class);

        pizzeriaConfig.update(
                3,
                2,
                new OneStepStrategy(startEventManager, completedEventManager),
                CustomerGenerationStrategy.MEDIUM
        );

        eventRegistry.registerEventManager(DishPreparationStartedEvent.class);
        eventRegistry.registerEventManager(DishPreparationCompletedEvent.class);
    }

    @Override
    public List<MenuItem> getMenu() {
        return menuService.getMenu().getMenuItems();
    }

    @Override
    public SimulationConfig getSimulationConfig() {
        return pizzeriaConfig;
    }

    @Override
    public void setSimulationConfig(SimulationConfig config) {
        pizzeriaConfig.update(config);
    }

    @Override
    public SimulationConfig mapToSimulationConfig(StartConfigDto inputDto) {
        var cookStrategy = getStrategy(inputDto);
        var config = new SimulationConfig();
        config.update(inputDto.cooksNumber(), inputDto.cashRegistersNumber(),
                cookStrategy,CustomerGenerationStrategy.MEDIUM);
        return config;
    }

    private ICookStrategy getStrategy(StartConfigDto inputDto) {
        var startEventManager = eventRegistry.getEventManager(DishPreparationStartedEvent.class);
        var completedEventManager = eventRegistry.getEventManager(DishPreparationCompletedEvent.class);

        return inputDto.specializedCooksMode() ?
                new WholeDishStrategy(startEventManager, completedEventManager) :
                new OneStepStrategy(startEventManager, completedEventManager);
    }

    private WholeDishStrategy getDefaultStrategy() {
        var startEventManager = eventRegistry.getEventManager(DishPreparationStartedEvent.class);
        var completedEventManager = eventRegistry.getEventManager(DishPreparationCompletedEvent.class);

        return new WholeDishStrategy(startEventManager, completedEventManager);
    }
}
