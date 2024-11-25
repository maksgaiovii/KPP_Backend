package software.kosiv.pizzaflow.service;

import software.kosiv.pizzaflow.model.cook.CookStrategy;
import software.kosiv.pizzaflow.model.order.Order;

import java.util.UUID;

public interface CookService {
    void acceptOrder(Order order);
    
    void stop();
    
    void resume();
    
    void terminate();
    
    void stopCook(UUID id);
    
    void resumeCook(UUID id);
    
    void setCookCount(int count);
    
    void setCookStrategy(CookStrategy strategy);
}
