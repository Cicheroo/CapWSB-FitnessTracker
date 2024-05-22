package com.capgemini.wsb.fitnesstracker.user.api;

import com.capgemini.wsb.fitnesstracker.exception.api.NotFoundException;

/**
 * Exception indicating that the {@link User} was not found.
 */
@SuppressWarnings("squid:S110")
public class UserNotFoundException extends NotFoundException {

    /**
     * Constructor with custom message to indicate what happened.
     *
     * @param message message explaining what happened
     */
    public UserNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructs message about user that was not found with given id.
     *
     * @param id id of user that was not found
     */
    public UserNotFoundException(Long id) {
        this("User with ID=%s was not found".formatted(id));
    }
}
