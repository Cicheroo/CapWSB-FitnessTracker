package com.capgemini.wsb.fitnesstracker.user.internal;

/**
 * User dto which contains {@link com.capgemini.wsb.fitnesstracker.user.api.User} id and email.
 *
 * @param id The id of user
 * @param email The email of user
 */
record UserIdEmailDto(Long id, String email) {
}
