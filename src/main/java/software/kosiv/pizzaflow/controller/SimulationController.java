package software.kosiv.pizzaflow.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import software.kosiv.pizzaflow.service.ISimulationService;
import software.kosiv.pizzaflow.service.SimulationService;

import java.util.Map;

@RestController
@RequestMapping("/simulation")
public class SimulationController {
    private final ISimulationService simulationService;

    public SimulationController() {
        this.simulationService = new SimulationService();
    }

    @PostMapping("/start")
    public ResponseEntity<Map<String, String>> start() throws IllegalStateException {
        boolean started = simulationService.start();
        String responseMessage = "simulation " + (started ? "started" : "has already started");

        return ResponseEntity.ok(Map.of("message", responseMessage));
    }

    @PostMapping("/pause")
    public ResponseEntity<Map<String, String>> pause() throws IllegalStateException {
        boolean paused = simulationService.pause();
        String responseMessage = "simulation " + (paused ? "paused" : "is currently not running");

        return ResponseEntity.ok(Map.of("message", responseMessage));
    }

    @PostMapping("/terminate")
    public ResponseEntity<Map<String, String>> terminate() {
        simulationService.terminate();
        return ResponseEntity.ok(Map.of("message", "simulation terminated"));
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Map<String, String>> handleIllegalStateException(IllegalStateException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", ex.getMessage()));
    }
}
