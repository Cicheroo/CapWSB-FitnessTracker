package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import org.springframework.stereotype.Component;

/**
 * User mapper is a helper class managed by Spring Framework which is used to map {@link User} to corresponding Dto.
 */
@Component
class UserMapper {

    /**
     * Maps {@link User} to {@link UserDto}
     *
     * @param user The user which is the source
     * @return user dto
     */
    UserDto toDto(User user) {
        return new UserDto(user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getBirthdate(),
                user.getEmail());
    }

    /**
     * Maps {@link UserDto} to {@link User}
     *
     * @param userDto The userDto which is the source
     * @return user
     */
    User toEntity(UserDto userDto) {
        return new User(
                userDto.firstName(),
                userDto.lastName(),
                userDto.birthdate(),
                userDto.email());
    }

    /**
     * Maps {@link User} to {@link UserSimpleDataDto} which contains username and id.
     *
     * @param user The user which is the source
     * @return user with simple data
     */
    UserSimpleDataDto toUserSimpleDataDto(User user) {
        return new UserSimpleDataDto(user.getId(),
                user.getFirstName(),
                user.getLastName());
    }

    /**
     * Maps {@link User} to {@link UserIdEmailDto} which contains email and id.
     *
     * @param user The user which is the source
     * @return user's id and email
     */
    UserIdEmailDto toUserIdEmailDto(User user) {
        return new UserIdEmailDto(user.getId(), user.getEmail());
    }

    /**
     * Maps {@link com.capgemini.wsb.fitnesstracker.user.api.UserDto} to {@link User} and updated user's fields.
     *
     * @param user The user to be updated
     * @param userDto The userDto which contains updated fields
     * @return updated user
     */
    User updateEntity(User user, com.capgemini.wsb.fitnesstracker.user.api.UserDto userDto) {
        user.setFirstName(userDto.firstName());
        user.setLastName(userDto.lastName());
        user.setBirthdate(userDto.birthdate());
        user.setEmail(userDto.email());
        return user;
    }
}
