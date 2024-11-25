package software.kosiv.pizzaflow.model.order;

import software.kosiv.pizzaflow.model.dish.Dish;
import software.kosiv.pizzaflow.model.menu.MenuItem;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

public class OrderItem {
    private final UUID id = UUID.randomUUID();
    private final Order order;
    private final MenuItem menuItem;
    private Dish dish;
    private final AtomicBoolean isBeingPrepared = new AtomicBoolean(false);

    public OrderItem(Order order, MenuItem menuItem) {
        this.order = order;
        this.menuItem = menuItem;
    }
    
    public void complete() {
        order.completeOrderItem(this);
    }
    
    public Order getOrder() {
        return order;
    }
    
    public UUID getId() {
        return id;
    }
    
    public Dish getDish() {
        if(dish == null) {
            this.dish = menuItem.createDish(this);
        }
        return dish;
    }
    
    public MenuItem getMenuItem() {
        return menuItem;
    }

    public boolean tryLockForPreparation() {
        return isBeingPrepared.compareAndSet(false, true);
    }

    public void unlockAfterPreparation() {
        isBeingPrepared.set(false);
    }

    public boolean isBeingPrepared() {
        return isBeingPrepared.get();
    }
}
