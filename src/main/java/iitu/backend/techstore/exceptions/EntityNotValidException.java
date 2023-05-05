package iitu.backend.techstore.exceptions;

public class EntityNotValidException extends EntityException {
    public EntityNotValidException() {
        super();
    }

    public EntityNotValidException(String message) {
        super(message);
    }
}
