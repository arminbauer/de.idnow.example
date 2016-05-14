package dao;

/**
 * Created by nick on 13.05.16.
 */
public class EntityNotFountException extends RuntimeException {

    public EntityNotFountException(String message) {
        super(message);
    }
}
