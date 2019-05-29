package ru.cheranev.rental.common;

/**
 * @author Cheranev N.
 * created on 19.05.2019.
 */
public class AppException extends RuntimeException {

    public AppException(String message) {
        super(message);
    }

    public AppException(String message, Throwable cause) {
        super(message, cause);
    }
}
