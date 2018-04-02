package ua.nure.events;

import org.apache.log4j.Logger;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class ServiceListener {

    static Logger logger = Logger.getLogger(ServiceListener.class.getName());

    @EventListener
    public void onApplicationEvent(ServiceEvent serviceEvent) {
        String className = serviceEvent.getSource().getClass().getSimpleName();
        switch (className) {
            case "CountryService":
                logger.info(logFun.apply(serviceEvent));
                break;
            case "LocationService":
                ///.....

        }
    }

    private Function<ServiceEvent, String> logFun = serviceEvent -> serviceEvent.getSource().getClass().getName() + " operation : " + serviceEvent.getOperationType() + " with  " + serviceEvent.getMeta().toString();
}
