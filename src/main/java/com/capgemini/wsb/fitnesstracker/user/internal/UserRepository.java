package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * User repository which extends JpaRepository. This repository is managed by Spring Framework and is used for operations
 * on {@link User} user entity.
 */
interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Query searching users by email address. It matches by exact match.
     *
     * @param email email of the user to search
     * @return {@link Optional} containing found user or {@link Optional#empty()} if none matched
     */
    default Optional<User> findByEmail(String email) {
        return findAll().stream()
                .filter(user -> Objects.equals(user.getEmail(), email))
                .findFirst();
    }

    /**
     * Query searching users by email address. This method is case-insensitive and allows to search by part of the email.
     *
     * @param email email pattern to search
     * @return {@link List} containing found users or empty {@link List} if no email matches.
     */
    default List<User> findByEmailLike(String email) {
        return findAll().stream()
                .filter(user -> user.getEmail().toLowerCase().contains(email.toLowerCase()))
                .toList();
    }

    /**
     * Query searching users older than specified time.
     *
     * @param time Upper bound limiting users maximum birthdate.
     * @return {@link List} containing found users or empty {@link List} if no older users found.
     */
    default List<User> findUsersOlderThan(LocalDate time) {
        return findAll().stream()
                .filter(user -> user.getBirthdate().isBefore(time))
                .toList();
    }
}
