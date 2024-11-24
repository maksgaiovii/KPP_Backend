package software.kosiv.pizzaflow.model;

import software.kosiv.pizzaflow.exception.BusyCookException;
import software.kosiv.pizzaflow.exception.PausedCookException;
import software.kosiv.pizzaflow.service.ICookStrategy;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class Cook {
    private final UUID id = UUID.randomUUID();
    private String name;
    private CookStatus status = CookStatus.FREE;
    private ICookStrategy strategy;
    private final Set<DishState> availableSkills = new HashSet<>();
    
    public Cook(String name) {
        this.name = name;
    }

    public DishState prepareDish(OrderItem orderItem) {
        var dish = orderItem.getDish();
        if (this.getStatus() == CookStatus.PAUSED) {
            throw new PausedCookException("Paused cook can't cook dish");
        }

        if (!canCook(dish)) {
            throw new IllegalArgumentException("Cook can't cook this dish state " + dish.getState());
        }

        var newState = strategy.prepareDish(dish);
        orderItem.unlockAfterPreparation();
        return newState;
    }
    
    public synchronized CookStatus setPaused() {
        var prevStatus = getStatus();
        this.status = CookStatus.PAUSED;
        strategy.setPaused();
        return prevStatus;
    }
    
    public synchronized CookStatus setBusy() {
        if (getStatus() == CookStatus.BUSY) {
            throw new BusyCookException("Cook is already busy");
        }

        var prevStatus = getStatus();
        this.status = CookStatus.BUSY;
        return prevStatus;
    }
    
    public synchronized CookStatus setFree() {
        var prevStatus = getStatus();
        this.status = CookStatus.FREE;
        strategy.setFree();
        return prevStatus;
    }

    public void setStrategy(ICookStrategy strategy) {
        this.strategy = strategy;
        this.strategy.setCook(this);
    }

    public boolean canCook(Dish dish) {
        return availableSkills.contains(dish.getNextState());
    }

    public void addSkill(DishState skill) {
        availableSkills.add(skill);
    }

    public void removeSkill(DishState skill) {
        availableSkills.remove(skill);
    }

    public Set<DishState> getSkills() {
        return availableSkills;
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

    @Override
    public String toString() {
        return "Cook{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", status=" + status +
                '}';
    }
}
