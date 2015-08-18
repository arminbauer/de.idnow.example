package controllers.core;

public class DuplicatedEntityException extends Exception {

    private final Long entityId;

    public DuplicatedEntityException(Long entityId) {
        this.entityId = entityId;
    }

    public DuplicatedEntityException(Long entityId, String message) {
        super(message);
        this.entityId = entityId;
    }

    public Long getEntityId() {
        return entityId;
    }
}
