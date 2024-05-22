package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.UserDto;
import com.capgemini.wsb.fitnesstracker.user.api.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * This service is main communication point between API and internal functionality. This service is managed by Spring Framework therefore
 * it's life cycle is managed by the framework.
 */
@Service
@RequiredArgsConstructor
@Slf4j
class UserServiceImpl implements UserService, UserProvider {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    /**
     * Creates user from given request {@link User}.
     * Additionally, checks if user already has id. If it does, an {@link IllegalArgumentException} exception is thrown.
     *
     * @param user The user to be created.
     * @return created user
     */
    @Override
    public User createUser(final User user) {
        log.info("Creating User {}", user);
        if (user.getId() != null) {
            throw new IllegalArgumentException("User has already DB ID, update is not permitted!");
        }
        return userRepository.save(user);
    }

    /**
     * Deletes user {@link User} with given id.
     * Additionally, checks if user with given id exists, if not an {@link UserNotFoundException} exception is thrown.
     *
     * @param id The id of user to be deleted
     */
    @Override
    public void deleteUserById(Long id) {
        log.info("Deleting User {}", id);
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        userRepository.deleteById(id);
    }

    /**
     * Updates user {@link User} specified by id with given {@link UserDto} request.
     * Additionally, checks if user with given id exists, if not an {@link UserNotFoundException} exception is thrown.
     *
     * @param id      The id of user to update
     * @param userDto The body of user to be updated
     * @return updated user
     */
    @Override
    public User updateUser(Long id, UserDto userDto) {
        log.info("Updating User {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        User updatedUser = userMapper.updateEntity(user, userDto);

        return userRepository.save(updatedUser);
    }

    /**
     * Retrieves a user based on their ID.
     * If the user with given ID is not found, then {@link Optional#empty()} will be returned.
     *
     * @param userId id of the user to be searched
     * @return An {@link Optional} containing the located user, or {@link Optional#empty()} if not found
     */
    @Override
    public Optional<User> getUser(final Long userId) {
        return userRepository.findById(userId);
    }

    /**
     * Retrieves a user based on their email.
     * If the user with given email is not found, then {@link Optional#empty()} will be returned.
     *
     * @param email The email of the user to be searched
     * @return An {@link Optional} containing the located user, or {@link Optional#empty()} if not found
     */
    @Override
    public Optional<User> getUserByEmail(final String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Retrieves all users.
     *
     * @return An {@link Optional} containing the all users,
     */
    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Retrieves users based on their email. This method is case-insensitive and allows to search by part of the email.
     * If no users are found, then empty {@link List} is returned.
     *
     * @param email The email to search upon.
     * @return A {@link List} containing users with matching email or empty {@link List} if no matches were found.
     */
    @Override
    public List<User> getUserByEmailLike(String email) {
        return userRepository.findByEmailLike(email);
    }

    /**
     * Retrieves users that were born before specified time.
     * If no users are found, then empty {@link List} is returned.
     *
     * @param time Upper bound limiting users maximum birthdate.
     * @return A {@link List} containing users that are older than specified time or empty {@link List} if no matches were found.
     */
    @Override
    public List<User> getUsersOlderThan(LocalDate time) {
        return userRepository.findUsersOlderThan(time);
    }

}