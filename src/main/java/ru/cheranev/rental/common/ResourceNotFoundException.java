package ru.cheranev.rental.common;

/**
 * @author Cheranev N.
 * created on 29.05.2019.
 */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
