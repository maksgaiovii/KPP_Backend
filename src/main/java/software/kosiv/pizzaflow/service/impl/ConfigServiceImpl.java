package software.kosiv.pizzaflow.service.impl;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import software.kosiv.pizzaflow.config.SimulationConfig;
import software.kosiv.pizzaflow.dto.StartConfigDto;
import software.kosiv.pizzaflow.model.cook.CookStrategy;
import software.kosiv.pizzaflow.model.cook.OneStepStrategy;
import software.kosiv.pizzaflow.model.cook.WholeDishStrategy;
import software.kosiv.pizzaflow.model.menu.MenuItem;
import software.kosiv.pizzaflow.service.ConfigService;
import software.kosiv.pizzaflow.service.MenuService;

import java.util.List;

@Service
public class ConfigServiceImpl implements ConfigService {
    private final SimulationConfig pizzeriaConfig;
    private final MenuService menuService;
    
    public ConfigServiceImpl(SimulationConfig pizzeriaConfig, MenuService menuService) {
        this.pizzeriaConfig = pizzeriaConfig;
        this.menuService = menuService;
    }

    @PostConstruct
    public void initDefaultConfig() {
        pizzeriaConfig.update(
                3,
                2,
                new WholeDishStrategy(),
                CustomerGenerationFrequency.MEDIUM
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
                      cookStrategy, CustomerGenerationFrequency.MEDIUM);
        return config;
    }
    
    private CookStrategy getStrategy(StartConfigDto inputDto) {
        return inputDto.specializedCooksMode() ? new WholeDishStrategy() : new OneStepStrategy();
    }
}
