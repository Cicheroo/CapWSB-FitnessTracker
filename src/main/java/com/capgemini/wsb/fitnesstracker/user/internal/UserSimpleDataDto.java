package com.capgemini.wsb.fitnesstracker.user.internal;

/**
 * Simple user data dto containing {@link com.capgemini.wsb.fitnesstracker.user.api.User} data.
 *
 * @param id The id of user
 * @param firstName The firstName of user
 * @param lastName The lastName of user
 */
record UserSimpleDataDto(Long id, String firstName, String lastName) {
}
