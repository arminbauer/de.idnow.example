package controllers.core;

public class EntityNotFoundException extends Exception {

    private final Long entityId;

    public EntityNotFoundException(Long entityId) {
        this.entityId = entityId;
    }

    public EntityNotFoundException(Long entityId, String message) {
        super(message);
        this.entityId = entityId;
    }

    public Long getEntityId() {
        return entityId;
    }
}
