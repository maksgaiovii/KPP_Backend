package software.kosiv.pizzaflow.service;

public interface ISimulationService {
    boolean start() throws IllegalStateException;
    boolean pause() throws IllegalStateException;
    void terminate() throws IllegalStateException;
}
