package software.kosiv.pizzaflow.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;

import java.time.LocalDateTime;

public class TestLogger {
    private static final Logger logger = LoggerFactory.getLogger(TestLogger.class);

    public static void onEvent(ApplicationEvent event) {
        logger.info("Event: {} : {}Thread: {}",
                event,
                LocalDateTime.now(),
                Thread.currentThread().getName());
    }

}
