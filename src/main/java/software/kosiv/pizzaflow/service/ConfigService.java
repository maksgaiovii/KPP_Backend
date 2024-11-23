package software.kosiv.pizzaflow.service;

import jakarta.annotation.PostConstruct;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import software.kosiv.pizzaflow.config.SimulationConfig;
import software.kosiv.pizzaflow.dto.StartConfigDto;
import software.kosiv.pizzaflow.model.MenuItem;

import java.util.List;

@Service
public class ConfigService implements IConfigService {
    private final SimulationConfig pizzeriaConfig;
    private final MenuService menuService;
    private final ApplicationEventPublisher eventPublisher;

    public ConfigService(SimulationConfig pizzeriaConfig, MenuService menuService, ApplicationEventPublisher eventPublisher) {
        this.pizzeriaConfig = pizzeriaConfig;
        this.menuService = menuService;
        this.eventPublisher = eventPublisher;
    }

    @PostConstruct
    public void initDefaultConfig() {
        pizzeriaConfig.update(
                3,
                2,
                new WholeDishStrategy(eventPublisher),
                CustomerGenerationStrategy.MEDIUM
        );
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
        ICookStrategy cookStrategy = inputDto.specializedCooksMode() ?
                new WholeDishStrategy(eventPublisher) : new OneStepStrategy(eventPublisher);

        var config = new SimulationConfig();
        config.update(inputDto.cooksNumber(), inputDto.cashRegistersNumber(),
                cookStrategy,CustomerGenerationStrategy.MEDIUM);
        return config;
    }
}
