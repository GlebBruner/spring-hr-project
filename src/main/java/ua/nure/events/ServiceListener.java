package ua.nure.events;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationListener;

public class ServiceListener implements ApplicationListener<ServiceEvent> {

    static Logger logger = Logger.getLogger(ServiceListener.class.getName());

    @Override
    public void onApplicationEvent(ServiceEvent serviceEvent) {
        String className = serviceEvent.getSource().getClass().getSimpleName();
        switch (className) {
            case "CountryService":
                switch (serviceEvent.getOperationType()) {
                    case CREATE:
                        logger.info(serviceEvent.getSource().getClass().getName() + " operation : " + serviceEvent.getOperationType() + " with  " + serviceEvent.getMeta().toString());
                        break;
                    case FIND:
                        logger.info(serviceEvent.getSource().getClass().getName() + " operation : " + serviceEvent.getOperationType() + " with " + serviceEvent.getMeta().toString());
                        break;
                    case FINDALL:
                        logger.info(serviceEvent.getSource().getClass().getName() + " operation : " + serviceEvent.getOperationType() + " with " + serviceEvent.getMeta().toString());
                        break;
                    case DELETE:
                        logger.info(serviceEvent.getSource().getClass().getName() + " operation : " + serviceEvent.getOperationType() + " with " + serviceEvent.getMeta().toString());
                        break;
                    case UPDATE:
                        logger.info(serviceEvent.getSource().getClass().getName() + " operation : " + serviceEvent.getOperationType() + " with " + serviceEvent.getMeta().toString());
                        break;
                }
                break;
            case "LocationService":
                ///.....

        }
    }
}
