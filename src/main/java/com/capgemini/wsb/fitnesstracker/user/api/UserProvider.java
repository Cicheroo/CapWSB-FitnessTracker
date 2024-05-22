package com.capgemini.wsb.fitnesstracker.user.api;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Interface (API) for querying {@link User} entities through the API.
 */
public interface UserProvider {

    /**
     * Retrieves a user based on their ID.
     * If the user with given ID is not found, then {@link Optional#empty()} will be returned.
     *
     * @param userId id of the user to be searched
     * @return An {@link Optional} containing the located user, or {@link Optional#empty()} if not found
     */
    Optional<User> getUser(Long userId);

    /**
     * Retrieves a user based on their email.
     * If the user with given email is not found, then {@link Optional#empty()} will be returned.
     *
     * @param email The email of the user to be searched
     * @return An {@link Optional} containing the located user, or {@link Optional#empty()} if not found
     */
    Optional<User> getUserByEmail(String email);

    /**
     * Retrieves all users.
     *
     * @return An {@link Optional} containing the all users,
     */
    List<User> findAllUsers();

    /**
     * Retrieves users based on their email. This method is case-insensitive and allows to search by part of the email.
     * If no users are found, then empty {@link List} is returned.
     *
     *
     * @param email The email to search upon.
     * @return A {@link List} containing users with matching email or empty {@link List} if no matches were found.
     */
    List<User> getUserByEmailLike(String email);

    /**
     * Retrieves users that were born before specified time.
     * If no users are found, then empty {@link List} is returned.
     *
     * @param time Upper bound limiting users maximum birthdate.
     * @return A {@link List} containing users that are older than specified time or empty {@link List} if no matches were found.
     */
    List<User> getUsersOlderThan(LocalDate time);
}
