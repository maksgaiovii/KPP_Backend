package software.kosiv.pizzaflow.service.impl;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import software.kosiv.pizzaflow.config.SimulationConfig;
import software.kosiv.pizzaflow.service.*;

import java.util.UUID;

@Getter
@Setter
@Service
public class SimulationServiceImpl implements SimulationService {
    public SimulationStatus simulationStatus = SimulationStatus.INITIAL;
    private CookService cookService;
    private CashRegisterService cashRegisterService;
    private CustomerService customerService;
    private MenuService menuService;
    
    public SimulationServiceImpl(CookService cookService,
                                 CashRegisterService cashRegisterService,
                                 MenuService menuService,
                                 CustomerService customerService) {
        this.cookService = cookService;
        this.cashRegisterService = cashRegisterService;
        this.menuService = menuService;
        this.customerService = customerService;
    }

    @Override
    public boolean start(SimulationConfig simulationConfig) throws IllegalStateException {
        if (simulationStatus == SimulationStatus.RUNNING) {
            throw new IllegalStateException("Simulation has already started");
        }

        cookService.setCookCount(simulationConfig.getCooksNumber());
        cookService.setCookStrategy(simulationConfig.getCookStrategy());
        cashRegisterService.setCashRegistersCount(simulationConfig.getCashRegistersNumber());
        customerService.setStrategy(simulationConfig.getCustomerGenerationFrequency());

        simulationStatus = SimulationStatus.RUNNING;
        return true;
    }

    @Override
    public boolean resume() throws IllegalStateException {
        if (simulationStatus != SimulationStatus.PAUSED)
        {
            throw new IllegalStateException("Simulation is not PAUSED");
        }

        cookService.resume();
        customerService.resume();

        simulationStatus = SimulationStatus.RUNNING;
        return true;
    }

    @Override
    public boolean stop() throws IllegalStateException {
        if (simulationStatus != SimulationStatus.RUNNING) {
            throw new IllegalStateException("Simulation is currently not running");
        }

        cookService.stop();
        customerService.stop();

        simulationStatus = SimulationStatus.PAUSED;
        return true;
    }

    @Override
    public void terminate() throws IllegalStateException {
        if (simulationStatus == SimulationStatus.INITIAL) {
            throw new IllegalStateException("Simulation has not started yet");
        }

        if (simulationStatus == SimulationStatus.TERMINATED) {
            throw new IllegalStateException("Simulation has already terminated");
        }

        cookService.terminate();
        customerService.terminate();

        simulationStatus = SimulationStatus.TERMINATED;
    }

    @Override
    public void stopCook(UUID id) throws IllegalStateException {
        cookService.stopCook(id);
    }

    @Override
    public void resumeCook(UUID id) throws IllegalStateException {
        cookService.resumeCook(id);
    }
}
