package software.kosiv.pizzaflow.service;

import software.kosiv.pizzaflow.config.SimulationConfig;

public interface ISimulationService {

    boolean start(SimulationConfig simulationConfig) throws IllegalStateException;
    boolean resume() throws IllegalStateException;
    boolean stop() throws IllegalStateException;
    void terminate() throws IllegalStateException;
}

