package software.kosiv.pizzaflow.model;

import software.kosiv.pizzaflow.exception.BusyCookException;
import software.kosiv.pizzaflow.exception.PausedCookException;

import java.util.Objects;
import java.util.UUID;

public class Cook {
    private final UUID id = UUID.randomUUID();
    private String name;
    private CookStatus status = CookStatus.FREE;
    
    public Cook(String name) {
        this.name = name;
    }
    
    public DishState prepareDish(Dish dish) {
        if (this.getStatus() == CookStatus.PAUSED) {
            throw new PausedCookException("Paused cook can't cook dish");
        }
        return dish.toNextState();
    }
    
    public DishState completeDish(Dish dish) { // todo: rename this method
        while (!dish.isCompleted()) {
            prepareDish(dish);
        }
        return dish.getState();
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Cook cook)) {
            return false;
        }
        return Objects.equals(id, cook.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
    
    public CookStatus setPaused() {
        var prevStatus = getStatus();
        this.status = CookStatus.PAUSED;
        return prevStatus;
    }
    
    public CookStatus setBusy() {
        if (getStatus() == CookStatus.BUSY) {
            throw new BusyCookException("Cook is already busy");
        }
        var prevStatus = getStatus();
        this.status = CookStatus.BUSY;
        return prevStatus;
    }
    
    public CookStatus setFree() {
        var prevStatus = getStatus();
        this.status = CookStatus.FREE;
        return prevStatus;
    }
    
    public UUID getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public CookStatus getStatus() {
        return status;
    }
    
    private void setStatus(CookStatus status) {
        this.status = status;
    }
    
    public boolean isAvailable() {
        return this.status == CookStatus.FREE;
    }
}
