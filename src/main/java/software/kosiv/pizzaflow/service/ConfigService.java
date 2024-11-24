package software.kosiv.pizzaflow.service;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import software.kosiv.pizzaflow.config.SimulationConfig;
import software.kosiv.pizzaflow.dto.StartConfigDto;
import software.kosiv.pizzaflow.model.ICookStrategy;
import software.kosiv.pizzaflow.model.MenuItem;
import software.kosiv.pizzaflow.model.OneStepStrategy;
import software.kosiv.pizzaflow.model.WholeDishStrategy;

import java.util.List;

@Service
public class ConfigService implements IConfigService {
    private final SimulationConfig pizzeriaConfig;
    private final MenuService menuService;

    public ConfigService(SimulationConfig pizzeriaConfig, MenuService menuService) {
        this.pizzeriaConfig = pizzeriaConfig;
        this.menuService = menuService;
    }

    @PostConstruct
    public void initDefaultConfig() {
        pizzeriaConfig.update(
                3,
                2,
                new WholeDishStrategy(),
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
        var cookStrategy = getStrategy(inputDto);
        var config = new SimulationConfig();
        config.update(inputDto.cooksNumber(), inputDto.cashRegistersNumber(),
                cookStrategy,CustomerGenerationStrategy.MEDIUM);
        return config;
    }

    private ICookStrategy getStrategy(StartConfigDto inputDto) {
        return inputDto.specializedCooksMode() ? new WholeDishStrategy() : new OneStepStrategy();
    }
}
