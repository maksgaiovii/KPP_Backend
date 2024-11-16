package software.kosiv.pizzaflow.service;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
@Setter
@Service
public class SimulationService implements ISimulationService{
    
    public SimulationStatus simulationStatus = SimulationStatus.INITIAL;
    
    @Override
    public boolean start() throws IllegalStateException {
        return false;
    }
    
    @Override
    public boolean pause() throws IllegalStateException {
        return false;
    }
    
    @Override
    public void terminate() throws IllegalStateException {

    }
    
}
