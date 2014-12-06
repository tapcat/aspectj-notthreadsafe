package net.tapcat.notthreadsafe;

/**
 * @author Maksim Zakharov
 * @since 1.0
 */
public class NotThreadSafeException extends Exception {

    public NotThreadSafeException(String message) {
        super(message);
    }
}
