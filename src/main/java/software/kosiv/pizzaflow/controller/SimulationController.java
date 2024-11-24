package software.kosiv.pizzaflow.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import software.kosiv.pizzaflow.service.ConfigService;
import software.kosiv.pizzaflow.service.ISimulationService;
import software.kosiv.pizzaflow.service.SimulationService;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/simulation")
public class SimulationController {
    private final ISimulationService simulationService;
    private final ConfigService configService;

    public SimulationController(SimulationService simulationService , ConfigService configService) {
        this.simulationService = simulationService;
        this.configService = configService;
        configService.initDefaultConfig();
    }

    @PostMapping("/start")
    public ResponseEntity<Map<String, String>> start() throws IllegalStateException {
        boolean started = simulationService.start(configService.getSimulationConfig());
        String responseMessage = "simulation " + (started ? "started" : "has already started");

        return ResponseEntity.ok(Map.of("message", responseMessage));
    }

    @PostMapping("/stop")
    public ResponseEntity<Map<String, String>> stop() throws IllegalStateException {
        boolean stopped = simulationService.stop();
        String responseMessage = "simulation " + (stopped ? "stopped" : "is currently not running");

        return ResponseEntity.ok(Map.of("message", responseMessage));
    }

    @PostMapping("/resume")
    public ResponseEntity<Map<String, String>> resume() throws IllegalStateException {
        boolean resumed = simulationService.resume();
        String responseMessage = "simulation " + (resumed ? "resumed" : "is not PAUSED");

        return ResponseEntity.ok(Map.of("message", responseMessage));
    }

    @PostMapping("/terminate")
    public ResponseEntity<Map<String, String>> terminate() {
        simulationService.terminate();
        return ResponseEntity.ok(Map.of("message", "simulation terminated"));
    }

    @PostMapping("/stop/{id}")
    public ResponseEntity<Map<String, String>> stopCook(@PathVariable String id) {
        simulationService.stopCook(UUID.fromString(id));
        return ResponseEntity.ok(Map.of("message", "cook stopped"));
    }

    @PostMapping("/resume/{id}")
    public ResponseEntity<Map<String, String>> resumeCook(@PathVariable String id) {
        simulationService.resumeCook(UUID.fromString(id));
        return ResponseEntity.ok(Map.of("message", "cook resumed"));
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Map<String, String>> handleIllegalStateException(IllegalStateException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", ex.getMessage()));
    }
}
