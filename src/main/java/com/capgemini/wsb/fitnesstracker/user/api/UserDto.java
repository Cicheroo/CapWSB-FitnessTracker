package com.capgemini.wsb.fitnesstracker.user.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.annotation.Nullable;

import java.time.LocalDate;

/**
 * User dto which is used to do write operations on {@link User}.
 *
 * @param id The id of user which is nullable
 * @param firstName The firstName of user
 * @param lastName The lastName of user
 * @param birthdate The birthdate of user
 * @param email The email of user
 */
public record UserDto(@Nullable Long id, String firstName, String lastName,
                      @JsonFormat(pattern = "yyyy-MM-dd") LocalDate birthdate,
                      String email) {

}
