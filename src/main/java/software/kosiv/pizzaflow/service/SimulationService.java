package software.kosiv.pizzaflow.service;

import software.kosiv.pizzaflow.config.SimulationConfig;

import java.util.UUID;

public interface SimulationService {

    boolean start(SimulationConfig simulationConfig) throws IllegalStateException;
    boolean resume() throws IllegalStateException;
    boolean stop() throws IllegalStateException;
    void terminate() throws IllegalStateException;
    void stopCook(UUID id) throws IllegalStateException;
    void resumeCook(UUID id) throws IllegalStateException;
}

