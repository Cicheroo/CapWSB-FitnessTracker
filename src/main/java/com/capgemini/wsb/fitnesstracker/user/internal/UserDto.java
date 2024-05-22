package com.capgemini.wsb.fitnesstracker.user.internal;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.annotation.Nullable;

import java.time.LocalDate;

/**
 * User dto which contains {@link com.capgemini.wsb.fitnesstracker.user.api.User} data.
 *
 * @param Id The id of user
 * @param firstName The firstName of user
 * @param lastName The lastName of user
 * @param birthdate The birthdate of user
 * @param email The email of user
 */
record UserDto(@Nullable Long Id, String firstName, String lastName,
               @JsonFormat(pattern = "yyyy-MM-dd") LocalDate birthdate,
               String email) {

}
