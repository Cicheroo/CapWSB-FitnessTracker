package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * User rest controller used to communicate via HTTP. It produces/consumes in JSON format as default.
 */
@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
class UserController {

    private final UserServiceImpl userService;

    private final UserMapper userMapper;

    /**
     * Finds all users with detailed data.
     *
     * @return list of users
     */
    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.findAllUsers()
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    /**
     * Finds all users with simple data.
     *
     * @return list of users
     */
    @GetMapping("/simple")
    public List<UserSimpleDataDto> getAllUsersSimpleData() {
        return userService.findAllUsers()
                .stream()
                .map(userMapper::toUserSimpleDataDto)
                .toList();
    }

    /**
     * Gets user by id and returns it's detailed data. If no user was found it throws exception.
     *
     * @param id of user to be found
     * @return found user
     */
    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable Long id) {
        return userService.getUser(id)
                .map(userMapper::toDto)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    /**
     * Gets user by email and returns it's detailed data. If no user was found it throws exception.
     *
     * @param email of user to be found
     * @return found user
     */
    @GetMapping("/email/{email}")
    public UserDto getUserByEmail(@PathVariable String email) {
        return userService.getUserByEmail(email)
                .map(userMapper::toDto)
                .orElseThrow(() -> new UserNotFoundException("User with EMAIL=%s was not found".formatted(email)));
    }

    /**
     * Gets list of user with email matching given parameter. If no matches found returns an empty {@link List}
     *
     * @param email pattern of email
     * @return list of users
     */
    @GetMapping("/email")
    public List<UserIdEmailDto> getUserByEmailLike(@RequestParam String email) {
        return userService.getUserByEmailLike(email)
                .stream()
                .map(userMapper::toUserIdEmailDto)
                .toList();
    }

    /**
     * Gets list of user with older than given time. If no matches found returns an empty {@link List}
     *
     * @param time Upper bound limiting users maximum birthdate.
     * @return list of users
     */
    @GetMapping("/older/{time}")
    public List<UserDto> getUsersOlderThan(@PathVariable LocalDate time) {
        return userService.getUsersOlderThan(time)
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    /**
     * Deletes user with given id.
     *
     * @param id The id of user to delete.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserById(@PathVariable Long id) {
        userService.deleteUserById(id);
    }

    /**
     * Adds new user. If successful it returns created user with 201 status code.
     *
     * @param userDto The body used to create new {@link User}
     * @return newly created user
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User addUser(@RequestBody UserDto userDto) {
        return userService.createUser(userMapper.toEntity(userDto));
    }

    /**
     * Updates already existing user.
     *
     * @param id The id of user to update
     * @param userDto The body used to update existing {@link User}
     * @return updated user
     */
    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody com.capgemini.wsb.fitnesstracker.user.api.UserDto userDto) {
        return userService.updateUser(id, userDto);
    }
}