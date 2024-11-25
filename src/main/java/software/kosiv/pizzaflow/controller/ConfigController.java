package software.kosiv.pizzaflow.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import software.kosiv.pizzaflow.config.SimulationConfig;
import software.kosiv.pizzaflow.dto.StartConfigDto;
import software.kosiv.pizzaflow.model.menu.MenuItem;
import software.kosiv.pizzaflow.service.IConfigService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/config")
public class ConfigController {
    private final IConfigService simulationConfigService;

    public ConfigController(IConfigService simulationConfigService) {
        this.simulationConfigService = simulationConfigService;
    }

    @GetMapping("/menu")
    public ResponseEntity<List<MenuItem>> getMenu() {
        return ResponseEntity.ok(simulationConfigService.getMenu());
    }

    @GetMapping()
    public ResponseEntity<SimulationConfig> getSimulationConfig() {
        return ResponseEntity.ok(simulationConfigService.getSimulationConfig());
    }

    @PutMapping()
    public ResponseEntity<SimulationConfig> setSimulationConfig
            (@RequestBody StartConfigDto config) {
        var simulationConfig = simulationConfigService.mapToSimulationConfig(config);
        simulationConfigService.setSimulationConfig(simulationConfig);
        return ResponseEntity.ok(simulationConfig);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleException(Exception ex) {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Internal server error" + ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
