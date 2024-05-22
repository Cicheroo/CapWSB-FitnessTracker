package com.capgemini.wsb.fitnesstracker.user.api;

/**
 * Interface (API) for modifying operations on {@link User} entities through the API.
 * Implementing classes are responsible for executing changes within a database transaction, whether by continuing an existing transaction or creating a new one if required.
 */
public interface UserService {

    /**
     * Creates user from given request {@link User}.
     * Implementing classes should handle cases when user already exists.
     *
     * @param user The user to be created.
     * @return created user
     */
    User createUser(User user);

    /**
     * Deletes user {@link User} with given id.
     * Implementing classes should handle cases when user does not exist.
     *
     * @param id The id of user to be deleted
     */
    void deleteUserById(Long id);

    /**
     * Updates user {@link User} specified by id with given {@link UserDto} request.
     * Implementing classes should handle cases when user does not exist.
     *
     * @param id The id of user to update
     * @param userDto The body of user to be updated
     * @return updated user
     */
    User updateUser(Long id, UserDto userDto);
}
