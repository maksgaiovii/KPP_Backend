package software.kosiv.pizzaflow.service;

import software.kosiv.pizzaflow.config.SimulationConfig;
import software.kosiv.pizzaflow.dto.StartConfigDto;
import software.kosiv.pizzaflow.model.menu.MenuItem;

import java.util.List;

public interface IConfigService {
    void initDefaultConfig();
    
    SimulationConfig mapToSimulationConfig(StartConfigDto inputDto);
    
    List<MenuItem> getMenu();
    
    SimulationConfig getSimulationConfig();
    
    void setSimulationConfig(SimulationConfig config);
}
