package ua.nure.events;


import org.springframework.context.ApplicationEvent;

public class ServiceEvent<T> extends ApplicationEvent {

    public enum OperationType {CREATE, DELETE, UPDATE, FIND, FINDALL}

    private OperationType operationType;
    private Object meta;

    ServiceEvent(T source, OperationType operationType, Object meta) {
        super(source);
        this.operationType = operationType;
        this.meta = meta;
    }

    OperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }

    Object getMeta() {
        return meta;
    }

    public void setMeta(Object meta) {
        this.meta = meta;
    }
}
