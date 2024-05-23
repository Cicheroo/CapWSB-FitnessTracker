package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserDto;
import org.springframework.stereotype.Component;

/**
 * Training mapper is a helper class managed by Spring Framework which is used to map {@link User} to corresponding Dto back and forth.
 */
@Component
class TrainingMapper {

    /**
     * Maps {@link Training} to {@link TrainingDto}
     *
     * @param training The training which is the source
     * @return training dto
     */
    TrainingDto toDto(Training training) {
        return new TrainingDto(training.getId(),
                toDto(training.getUser()),
                training.getStartTime(),
                training.getEndTime(),
                training.getActivityType(),
                training.getDistance(),
                training.getAverageSpeed());
    }

    /**
     * Maps {@link com.capgemini.wsb.fitnesstracker.training.api.TrainingDto} to {@link Training}
     *
     * @param trainingDto The training which is the source
     * @param user        The user assigned to training
     * @return training
     */
    Training toEntity(com.capgemini.wsb.fitnesstracker.training.api.TrainingDto trainingDto, User user) {
        return new Training(user,
                trainingDto.getStartTime(),
                trainingDto.getEndTime(),
                trainingDto.getActivityType(),
                trainingDto.getDistance(),
                trainingDto.getAverageSpeed());
    }

    /**
     * Maps {@link com.capgemini.wsb.fitnesstracker.training.api.TrainingDto} to {@link Training} and updates training's field.
     *
     * @param training The training to be updated
     * @param trainingDto The trainingDto which contains updated fields
     * @return updated training
     */
    Training updateEntity(Training training, com.capgemini.wsb.fitnesstracker.training.api.TrainingDto trainingDto) {
        training.setStartTime(trainingDto.getStartTime());
        training.setEndTime(trainingDto.getEndTime());
        training.setActivityType(trainingDto.getActivityType());
        training.setDistance(trainingDto.getDistance());
        training.setAverageSpeed(trainingDto.getAverageSpeed());
        return training;
    }

    private UserDto toDto(User user) {
        return new UserDto(user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getBirthdate(),
                user.getEmail());
    }
}
