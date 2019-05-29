package ru.cheranev.rental.rest;

/**
 * @author Cheranev N.
 * created on 29.05.2019.
 */
class ResourceNotFoundException extends RuntimeException {
    ResourceNotFoundException(String message) {
        super(message);
    }
}
