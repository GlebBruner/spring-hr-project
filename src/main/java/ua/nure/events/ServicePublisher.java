package ua.nure.events;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

@Component
public class ServicePublisher<T> implements ApplicationEventPublisherAware{

    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void publish (T source, ServiceEvent.OperationType operationType, Object meta) {
        this.applicationEventPublisher.publishEvent(new ServiceEvent(source, operationType, meta));
    }
}
