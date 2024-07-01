package systems.rishon.listeners;

import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;
import systems.rishon.Minestorm;
import systems.rishon.utils.Logger;

import java.lang.reflect.Method;
import java.util.Set;

public class EventHandler {

    // Logger
    private final Logger logger = Logger.getLogger();

    private final Minestorm instance;

    public EventHandler(Minestorm instance) {
        this.instance = instance;
        init();
    }

    private void init() {
        // Initialization
        processEvents();
    }

    public void processEvents() {
        logger.info("Processing events...");
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setUrls(ClasspathHelper.forJavaClassPath())
                .setScanners(Scanners.SubTypes)
                .filterInputsBy(new FilterBuilder().includePackage("systems.rishon.listeners")));

        Set<Class<? extends Listener>> listenerClasses = reflections.getSubTypesOf(Listener.class);

        for (Class<? extends Listener> listenerClass : listenerClasses) {
            try {
                Listener listener = listenerClass.getDeclaredConstructor().newInstance();

                Method[] methods = listenerClass.getDeclaredMethods();
                for (Method method : methods) {
                    logger.info("Processing event: " + method.getName());
                    if (method.isAnnotationPresent(IEvent.class)) {
                        IEvent annotation = method.getAnnotation(IEvent.class);
                        EventPriority priority = annotation.priority();
                        method.invoke(listener);
                    }
                }
            } catch (Exception e) {
                logger.error("Failed to process events: " + e.getMessage());
            }
        }
    }

    public Minestorm getInstance() {
        return instance;
    }

}
