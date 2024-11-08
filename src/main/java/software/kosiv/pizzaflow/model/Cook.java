package software.kosiv.pizzaflow.model;

import software.kosiv.pizzaflow.exception.BusyCookException;
import software.kosiv.pizzaflow.exception.CookStateException;
import software.kosiv.pizzaflow.exception.PausedCookException;

import java.util.Objects;
import java.util.UUID;

public class Cook<T extends Dish> {
    private final UUID id = UUID.randomUUID();
    private String name;
    private CookStatus status;
    
    public Cook(String name) {
        this.name = name;
    }
    
    public void cookDish(T dish) throws CookStateException {
        if (this.getStatus() == CookStatus.PAUSED) {
            throw new PausedCookException();
        }
        if (this.getStatus() == CookStatus.BUSY) {
            throw new BusyCookException();
        }
        setStatus(CookStatus.BUSY);
        dish.nextState();
        setStatus(CookStatus.FREE);
    }
    
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Cook<?> cook)) {
            return false;
        }
        return Objects.equals(id, cook.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hashCode(id);
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
    
    public void setStatus(CookStatus status) {
        this.status = status;
    }
}
