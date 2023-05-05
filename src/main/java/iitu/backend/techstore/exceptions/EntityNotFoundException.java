package iitu.backend.techstore.exceptions;

public class EntityNotFoundException extends EntityException {
    public EntityNotFoundException() {
        super();
    }

    public EntityNotFoundException(String message) {
        super(message);
    }
}
